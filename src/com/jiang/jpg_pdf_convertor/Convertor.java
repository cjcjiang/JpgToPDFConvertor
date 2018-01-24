package com.jiang.jpg_pdf_convertor;

public class Convertor {
	String convert_path = "/usr/local/Cellar/imagemagick/7.0.7-22/bin/convert ";
	String input_path;
	String output_path;
	
	Process process = null;
	
	public ConvertingResult convertor(String input_path, String output_path) {
		this.input_path = input_path;
		this.output_path = output_path;
		String bash_command = convert_path + input_path + output_path;
		String[] command = { "/bin/sh", "-c", bash_command };
		try {
			process = Runtime.getRuntime().exec(command);
		} catch(Exception e) {
			e.printStackTrace();
		}
		ConvertingResult result = new ConvertingResult(process, output_path);
		return result;
	}

}
