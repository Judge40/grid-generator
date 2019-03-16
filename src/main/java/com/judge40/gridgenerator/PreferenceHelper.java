/*
 * Grid Generator Copyright (c) 2019 Judge40
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.judge40.gridgenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

/**
 * A helper class for retrieving and setting preference values using {@link
 * java.util.prefs.Preferences}.
 */
public class PreferenceHelper {

  private static final Preferences PREFERENCES = Preferences
    .userNodeForPackage(GridGenerator.class);
  private static final int BYTE_CHUNK_SIZE = (int) (Preferences.MAX_VALUE_LENGTH * 0.75);

  private static final String CLASS_PARTICIPANTS = "%s/participants";

  private static final String PARTICIPANT_CLASS_NAMES = "participantClassNames";
  private static final String PARTICIPANT_VALIDATOR = "participantValidator";

  /**
   * Get the participants for a particular participant class.
   *
   * @param className The class name to get the participants for.
   * @return A list of the participants for the given class.
   * @throws BackingStoreException If the number of object chunks could not be determined.
   * @throws ClassNotFoundException If the preference values could not be recombined in to a valid
   * object.
   * @throws IOException If the preference values could not be read.
   */
  public static List<String> getClassParticipants(String className)
      throws BackingStoreException, ClassNotFoundException, IOException {
    Object preferenceObject = getObject(String.format(CLASS_PARTICIPANTS, className),
      Collections.emptyList());
    return ((List<?>) preferenceObject).stream().map(item -> (String) item)
      .collect(Collectors.toList());
  }

  /**
   * Set the participants for a particular class.
   *
   * @param className The class to set the participants for.
   * @param participants A list of the participants for the given class.
   * @throws BackingStoreException If the previous preference node value could not be cleared.
   * @throws IOException If the object could not be converted to a byte array.
   */
  public static void setClassParticipants(String className, List<String> participants)
    throws BackingStoreException, IOException {
    putObject(String.format(CLASS_PARTICIPANTS, className), participants);
  }

  /**
   * Get the participant class names.
   *
   * @return A list of participant class names.
   * @throws BackingStoreException If the number of object chunks could not be determined.
   * @throws ClassNotFoundException If the preference values could not be recombined in to a valid
   * object.
   * @throws IOException If the preference values could not be read.
   */
  public static List<String> getParticipantClassNames()
      throws BackingStoreException, ClassNotFoundException, IOException {
    Object preferenceObject = getObject(PARTICIPANT_CLASS_NAMES, Collections.emptyList());
    return ((List<?>) preferenceObject).stream().map(item -> (String) item)
      .collect(Collectors.toList());
  }

  /**
   * Set the participant class names.
   *
   * @param participantClassNames A list of participant class names.
   * @throws BackingStoreException If the previous preference node value could not be cleared.
   * @throws IOException If the object could not be converted to a byte array.
   */
  public static void setParticipantClassNames(List<String> participantClassNames)
      throws BackingStoreException, IOException {
    putObject(PARTICIPANT_CLASS_NAMES, participantClassNames);
  }

  /**
   * Get the validator for participants.
   *
   * @return The validator RegEx.
   */
  public static String getParticipantValidator() {
    return PREFERENCES.get(PARTICIPANT_VALIDATOR, "");
  }

  /**
   * Set the validator for participants.
   *
   * @param participantValidator The validator RegEx.
   */
  public static void setParticipantValidator(String participantValidator) {
    PREFERENCES.put(PARTICIPANT_VALIDATOR, participantValidator);
  }

  /**
   * Get a serializable object from the preference store, any object which were split will be
   * reconstructed.
   *
   * @param key The preference name.
   * @param defaultValue The default value to return if there is no preference set.
   * @return The preference value.
   * @throws BackingStoreException If the number of object chunks could not be determined.
   * @throws ClassNotFoundException If the preference values could not be recombined in to a valid
   * object.
   * @throws IOException If the preference values could not be read.
   */
  private static Object getObject(String key, Object defaultValue)
      throws BackingStoreException, ClassNotFoundException, IOException {
    Preferences preferenceNode = PREFERENCES.node(key);
    int numberOfChunks = preferenceNode.keys().length;

    // If no chunks were found then the preference does not exist, return the default value.
    if (numberOfChunks == 0) {
      return defaultValue;
    }

    byte[] objectBytes = null;

    // Iterate through the chunks and combine the bytes in to a single array.
    for (int i = numberOfChunks - 1; i >= 0; i--) {
      byte[] chunkBytes = preferenceNode.getByteArray(String.valueOf(i), null);
      int chunkStart = i * BYTE_CHUNK_SIZE;

      // If the byte array is not initialized, calculate the correct size and initialize it.
      if (objectBytes == null) {
        int objectSize = chunkStart + chunkBytes.length;
        objectBytes = new byte[objectSize];
      }

      // Copy the chunk bytes in to the object bytes array.
      System.arraycopy(chunkBytes, 0, objectBytes, chunkStart, chunkBytes.length);
    }

    // Convert the object bytes in to an object.
    try (ByteArrayInputStream bais = new ByteArrayInputStream(
      objectBytes); ObjectInputStream ois = new ObjectInputStream(bais)) {
      return ois.readObject();
    }
  }

  /**
   * Put a serializable object in to the preference store, the object will be split across as many
   * byte array preferences as required for the whole object to be stored.
   *
   * @param key The preference name.
   * @param object The preference value to store.
   * @throws BackingStoreException If the previous preference node value could not be cleared.
   * @throws IOException If the object could not be converted to a byte array.
   */
  private static void putObject(String key, Object object)
      throws BackingStoreException, IOException {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(object);
      byte[] objectBytes = baos.toByteArray();
      int numberOfChunks = (int) Math.ceil((double) objectBytes.length / BYTE_CHUNK_SIZE);

      // Clear any existing values in the preference node.
      Preferences preferenceNode = PREFERENCES.node(key);
      preferenceNode.clear();

      // Split the object's byte in to storable chunks.
      for (int i = 0; i < numberOfChunks; i++) {
        int start = i * BYTE_CHUNK_SIZE;
        int length = Math.min(BYTE_CHUNK_SIZE, objectBytes.length - start);
        byte[] chunkBytes = new byte[length];

        // Copy the object chunk in to the chunk array and store it.
        System.arraycopy(objectBytes, start, chunkBytes, 0, length);
        preferenceNode.putByteArray(String.valueOf(i), chunkBytes);
      }
    }
  }
}
