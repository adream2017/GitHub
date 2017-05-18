/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.io;

import com.lang.ExceptionUtil;
import com.lang.StringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
	public static String getName(String path) {
		if (StringUtil.isNullOrWhiteSpace(path))
			return null;
		return getName(new File(path));
	}

	public static String getName(File file) {
		if (file == null)
			return null;
		String name = file.getName();
		int index = name.lastIndexOf(".");
		return ((index >= 0) ? name.substring(0, index) : name);
	}

	public static String getType(String path) {
		String extension = null;
		if (((extension = getExtension(path)) == null)
				|| (extension.length() < 1))
			return null;
		return extension.substring(1);
	}

	public static String getType(File file) {
		return ((file == null) ? null : getType(file.getAbsolutePath()));
	}

	public static String getExtension(String path) {
		if (StringUtil.isNullOrWhiteSpace(path))
			return null;
		int index = path.lastIndexOf(".");
		return (((index < 0) || (index == path.length() - 1)) ? null : path
				.substring(index));
	}

	public static String getExtension(File file) {
		return ((file == null) ? null : getExtension(file.getAbsolutePath()));
	}

	public static void delete(String path) {
		if (StringUtil.isNullOrWhiteSpace(path))
			return;
		delete(new File(path));
	}

	public static void delete(File file) {
		if (!(file.exists()))
			return;
		deleteChildren(file);
		file.delete();
	}

	public static void deleteChildren(File dir) {
		if (!(dir.exists()))
			return;
		if (!(dir.isDirectory()))
			return;
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; ++i) {
			if (files[i].isDirectory())
				delete(files[i]);
			else
				files[i].delete();
		}
	}

	public static void copy(String sourcePath, String targetDirPath)
			throws IOException {
		ExceptionUtil.nullOrWhiteSpaceThrowException(sourcePath, "源文件或源目录不能为空");
		ExceptionUtil.nullOrWhiteSpaceThrowException(targetDirPath, "目标目录不能为空");
		copyOrMove(new File(sourcePath), new File(targetDirPath), false, false);
	}

	public static void copy(String sourcePath, String targetDirPath,
			boolean isOverFile) throws IOException {
		ExceptionUtil.nullOrWhiteSpaceThrowException(sourcePath, "源文件或源目录不能为空");
		ExceptionUtil.nullOrWhiteSpaceThrowException(targetDirPath, "目标目录不能为空");
		copyOrMove(new File(sourcePath), new File(targetDirPath), isOverFile,
				false);
	}

	public static void copy(File source, File targetDir) throws IOException {
		copyOrMove(source, targetDir, false, false);
	}

	public static void copy(File source, File targetDir, boolean isOverFile)
			throws IOException {
		copyOrMove(source, targetDir, isOverFile, false);
	}

	public static void move(String sourcePath, String targetDirPath)
			throws IOException {
		ExceptionUtil.nullOrWhiteSpaceThrowException(sourcePath, "源文件或源目录不能为空");
		ExceptionUtil.nullOrWhiteSpaceThrowException(targetDirPath, "目标目录不能为空");
		copyOrMove(new File(sourcePath), new File(targetDirPath), false, true);
	}

	public static void move(String sourcePath, String targetDirPath,
			boolean isOverFile) throws IOException {
		ExceptionUtil.nullOrWhiteSpaceThrowException(sourcePath, "源文件或源目录不能为空");
		ExceptionUtil.nullOrWhiteSpaceThrowException(targetDirPath, "目标目录不能为空");
		copyOrMove(new File(sourcePath), new File(targetDirPath), isOverFile,
				true);
	}

	public static void move(File source, File targetDir) throws IOException {
		copyOrMove(source, targetDir, false, true);
	}

	public static void move(File source, File targetDir, boolean isOverFile)
			throws IOException {
		copyOrMove(source, targetDir, isOverFile, true);
	}

	private static void copyOrMove(File source, File targetDir,
			boolean isOverFile, boolean isDeleteSource) throws IOException {
		if (!(source.exists()))
			throw new IOException("源文件或源目录不存在");
		if (!(targetDir.exists()))
			targetDir.mkdir();
		if (!(targetDir.isDirectory()))
			throw new IOException("targetDir必须是一个目录");
		if (source.isFile()) {
			copyOrMoveFile(source, new File(targetDir, source.getName()),
					isOverFile, isDeleteSource);
			return;
		}
		if (source.equals(targetDir))
			targetDir.mkdirs();
		File[] files = source.listFiles();
		for (int i = 0; i < files.length; ++i) {
			if (files[i].isFile()) {
				copyOrMoveFile(files[i],
						new File(targetDir, files[i].getName()), isOverFile,
						isDeleteSource);
			} else {
				if (!(files[i].isDirectory()))
					continue;
				copyOrMove(files[i], new File(targetDir, files[i].getName()),
						isOverFile, isDeleteSource);
				if (isDeleteSource)
					files[i].delete();
			}
		}
		if (isDeleteSource)
			source.delete();
	}

	private static void copyOrMoveFile(File sourceFile, File targetFile,
			boolean isOverFile, boolean isDeleteSourceFile) throws IOException {
		ExceptionUtil.nullThrowException(sourceFile, "源文件不能为空");
		String path = targetFile.getAbsolutePath();
		if (!(sourceFile.exists()))
			throw new IOException("源文件不存在");
		if (!(sourceFile.isFile()))
			throw new IOException("无效的源文件");
		if ((path.length() < 1) || (path.indexOf(".") <= 1)
				|| (path.charAt(path.length() - 1) == '.'))
			throw new IOException("无效的目标文件");
		if ((targetFile.exists()) && (!(isOverFile))) {
			String name = getName(targetFile);

			String extension = getExtension(targetFile);
			targetFile = new File(targetFile.getParent(), name + " - 副本"
					+ extension);

			for (int i = 2; targetFile.exists(); ++i) {
				targetFile = new File(targetFile.getParent(), name + " - 副本("
						+ i + ")" + extension);
			}
		}
		InputStream fis = new FileInputStream(sourceFile);

		OutputStream fos = new FileOutputStream(targetFile);
		byte[] buf = new byte[1024];
		while (true) {
			if (fis.available() < 1024) {
				for (int i = fis.read(); i != -1; i = fis.read())
					fos.write(i);
				break;
			}

			fis.read(buf);

			fos.write(buf);
		}

		fis.close();
		fos.close();
		if (isDeleteSourceFile)
			sourceFile.delete();
	}
}