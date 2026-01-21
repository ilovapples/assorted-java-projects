package dev.ilovapples;

public class IoManager {
	private boolean inputInit = false;

	public IoManager() { }

	public void setupInput() {
		this.inputInit = true;
		System.out.println("");
	}

	public void cleanupInput() {
		if (!this.inputInit) return;
		// todo
	}
}
