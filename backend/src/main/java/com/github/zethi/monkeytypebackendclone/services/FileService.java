package com.github.zethi.monkeytypebackendclone.services;

import com.github.zethi.monkeytypebackendclone.exceptions.CanNotCreateFileException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;

@Service
public class FileService {

    public void createFile(Path path) throws CanNotCreateFileException, FileAlreadyExistsException {
        File file = path.toFile();

        if (file.exists()) throw new FileAlreadyExistsException(file.getName());

        try {
            file.createNewFile();
        } catch (IOException exception) {
            throw new CanNotCreateFileException();
        }
    }

    public void createFile(Path path, String data) throws CanNotCreateFileException, IOException {
        this.createFile(path);
        this.write(path, data);
    }


    public String read(Path path) throws IOException {
        StringBuilder content = new StringBuilder();
        InputStream in = null;

        try {
            in = new FileInputStream(path.toFile());
            int b;
            while ((b = in.read()) != -1)
                content.append((char) b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert in != null;
            in.close();
        }

        return content.toString();
    }

    public void write(Path path, String content) throws IOException {
        try (
                RandomAccessFile stream = new RandomAccessFile(path.toFile(),"rw");
                FileChannel channel = stream.getChannel()
        ) {
            byte[] strBytes = content.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);

            buffer.put(strBytes);
            buffer.flip();
            channel.write(buffer);
        } catch (Exception e) {
            throw new IOException();
        }
    }

    public void delete(Path path) {
        path.toFile().delete();
    }

    public boolean exists(Path path) {
        return path.toFile().exists();
    }
}
