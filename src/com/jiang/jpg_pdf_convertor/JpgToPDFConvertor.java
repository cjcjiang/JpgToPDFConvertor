package com.jiang.jpg_pdf_convertor;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JpgToPDFConvertor {

	public static void main(String[] args) {
		
		ArrayList<File> jy_dir_list = new ArrayList<File>();
		
		ArrayList<ConvertingResult> process_list = new ArrayList<ConvertingResult>();
		
		// root_level_path is the root path of all of the photos
		String root_level_path = "/users/dingxiaoyan/desktop/jpg_conv_test";
		File root_level = new File(root_level_path);
		File[] jy_level = root_level.listFiles();
		
		// Record the running time of this program in the recording file
		LocalDateTime program_running_timestamp = LocalDateTime.now();
		String title = program_running_timestamp + " JpgToPDFConvertor Running\n";
		try {
			Files.write(Paths.get(root_level_path + "/record.txt"), title.getBytes(), StandardOpenOption.APPEND);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Detect all of the directories and files under the root path
		for(File path : jy_level) {
			// Get the list of all of the prisons
			if(path.isDirectory()) {
				jy_dir_list.add(path);
			}
		}
		
		// Get all of the zf_leverl path
		jy_dir_list.forEach(jy_path -> {
			File[] zf_level = jy_path.listFiles();
			ArrayList<File> zf_dir_list = new ArrayList<File>();
			
			for(File zf_path : zf_level) {
				// Get the list of all of the prisoners in this specific prison
				if(zf_path.isDirectory()) {
					zf_dir_list.add(zf_path);
				}
			}
			
			// Covert all of the photos to pdf
			zf_dir_list.forEach(path -> {
				String base_path = path.getPath();
				String photo_input_path = base_path + "/*.jpg ";
				String photo_output_path = base_path + "/output.pdf";
				Convertor photo_convertor = new Convertor();
//				int exec_code = photo_convertor.convertor(photo_input_path, photo_output_path);
//				System.out.println(exec_code);
				ConvertingResult result = photo_convertor.convertor(photo_input_path, photo_output_path);
				process_list.add(result);
			});
			
			// Monitoring all of the processes
			while(!process_list.isEmpty()) {
				Iterator<ConvertingResult> iter = process_list.iterator();
				while(iter.hasNext()) {
					ConvertingResult result = iter.next();
					Process process = result.getProcess();
					if(!process.isAlive()) {
						// This process's converting has finished
						String output_path = result.getOutput_path();
						LocalDateTime timestamp = LocalDateTime.now();
						String record = output_path + " generated on " + timestamp + "\n";
						iter.remove();
						System.out.println("The return value of process " + process + " is " + process.exitValue());
						System.out.println(record);
						// Write the generating-record to the recording file
						try {
							Files.write(Paths.get(root_level_path + "/record.txt"), record.getBytes(), StandardOpenOption.APPEND);
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		});
		
	}

}
