package com.jiang.jpg_pdf_convertor;

public class ConvertingResult {
	Process process = null;
	String output_path;
	
	public ConvertingResult(Process process, String output_path) {
		super();
		this.process = process;
		this.output_path = output_path;
	}

	public Process getProcess() {
		return process;
	}

	public String getOutput_path() {
		return output_path;
	}
	
}
