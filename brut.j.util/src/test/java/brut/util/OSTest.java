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

import brut.common.BrutException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class OSTest {

    @Test
    public void testMkdirSuccess() throws Exception {
        File tempDir = Files.createTempDirectory("apktool-test").toFile();
        try {
            File newDir = new File(tempDir, "testdir");
            assertFalse("Directory should not exist yet", newDir.exists());
            
            OS.mkdir(newDir);
            
            assertTrue("Directory should be created", newDir.exists());
            assertTrue("Should be a directory", newDir.isDirectory());
        } finally {
            OS.rmdir(tempDir);
        }
    }

    @Test
    public void testMkdirExistingDirectory() throws Exception {
        File tempDir = Files.createTempDirectory("apktool-test").toFile();
        try {
            // Should not throw if directory already exists
            OS.mkdir(tempDir);
            
            assertTrue("Directory should still exist", tempDir.exists());
            assertTrue("Should be a directory", tempDir.isDirectory());
        } finally {
            OS.rmdir(tempDir);
        }
    }

    @Test(expected = BrutException.class)
    public void testMkdirFailure() throws Exception {
        // Try to create a directory in a location that doesn't allow it
        // This test creates a file first, then tries to create a directory with the same name
        File tempDir = Files.createTempDirectory("apktool-test").toFile();
        try {
            File file = new File(tempDir, "testfile");
            assertTrue("Test file should be created", file.createNewFile());
            
            // Try to create a directory where a file already exists - this should fail
            File impossibleDir = new File(file, "subdir");
            OS.mkdir(impossibleDir); // Should throw BrutException
            
            fail("Should have thrown BrutException");
        } finally {
            OS.rmdir(tempDir);
        }
    }

    @Test
    public void testMkdirNestedDirectories() throws Exception {
        File tempDir = Files.createTempDirectory("apktool-test").toFile();
        try {
            File nestedDir = new File(tempDir, "level1/level2/level3");
            assertFalse("Nested directory should not exist yet", nestedDir.exists());
            
            OS.mkdir(nestedDir);
            
            assertTrue("Nested directory should be created", nestedDir.exists());
            assertTrue("Should be a directory", nestedDir.isDirectory());
        } finally {
            OS.rmdir(tempDir);
        }
    }
}
