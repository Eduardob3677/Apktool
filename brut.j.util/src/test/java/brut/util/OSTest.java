/*
 *  Copyright (C) 2010 Ryszard Wiśniewski <brut.alll@gmail.com>
 *  Copyright (C) 2010 Connor Tumbleson <connor.tumbleson@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package brut.util;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

public class OSTest {

    @Test
    public void testCreateTempFile() throws IOException {
        File tempFile = OS.createTempFile("TEST", ".tmp");
        
        assertNotNull("Temp file should not be null", tempFile);
        assertTrue("Temp file should exist", tempFile.exists());
        
        // Verify the temp file is created in $HOME/.temp directory
        String home = System.getenv("HOME");
        if (home == null || home.isEmpty()) {
            home = System.getProperty("user.home");
        }
        File expectedTempDir = new File(home, ".temp");
        assertTrue("Temp directory should exist", expectedTempDir.exists());
        assertTrue("Temp directory should be a directory", expectedTempDir.isDirectory());
        
        File parentDir = tempFile.getParentFile();
        assertEquals("Temp file should be in $HOME/.temp", expectedTempDir.getAbsolutePath(), parentDir.getAbsolutePath());
        
        // Clean up
        tempFile.delete();
    }

    @Test
    public void testCreateTempFileWithNullSuffix() throws IOException {
        File tempFile = OS.createTempFile("TEST", null);
        
        assertNotNull("Temp file should not be null", tempFile);
        assertTrue("Temp file should exist", tempFile.exists());
        
        // Clean up
        tempFile.delete();
    }
}
