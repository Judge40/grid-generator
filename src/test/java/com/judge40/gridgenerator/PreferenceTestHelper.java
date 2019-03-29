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
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

/**
 * A helper which handles backup and restore of preference values.
 */
public class PreferenceTestHelper {

  private Preferences preferences;
  private byte[] originalPreferenceBackup;

  /**
   * Performs a backup of the current preference values and then clears all preferences.
   */
  public PreferenceTestHelper() throws BackingStoreException, IOException {
    preferences = Preferences.userNodeForPackage(GridGenerator.class);
    backupPreferences();
    clearPreferences();
  }

  private void backupPreferences() throws BackingStoreException, IOException {
    // Create a backup of the original preferences values.
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      preferences.exportSubtree(baos);
      originalPreferenceBackup = baos.toByteArray();
    }
  }

  /**
   * Clear all of the existing preference values.
   */
  public void clearPreferences() throws BackingStoreException {
    preferences.clear();

    for (String childName : preferences.childrenNames()) {
      preferences.node(childName).removeNode();
    }
  }

  /**
   * Import the backed up preferences.
   */
  public void restorePreferences()
    throws BackingStoreException, IOException, InvalidPreferencesFormatException {
    clearPreferences();

    try (ByteArrayInputStream bais = new ByteArrayInputStream(originalPreferenceBackup)) {
      Preferences.importPreferences(bais);
    }
  }

  public Preferences getPreferences() {
    return preferences;
  }
}
