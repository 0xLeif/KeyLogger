package org.logme.client;

import java.io.Serializable;
import java.util.ArrayList;

public class SnapshotCollection implements Serializable {

	protected ArrayList<Snapshot> snapshots;
	protected String hostname;

	public SnapshotCollection(String name, ArrayList<Snapshot> snapshots) {
		this.snapshots = snapshots;
		this.hostname = name;
	}

	public void setSnapshots(ArrayList<Snapshot> snapshots) {
		this.snapshots = snapshots;
	}

	public ArrayList<Snapshot> getSnapshots() {
		return snapshots;
	}

	public String getHostname() {
		return hostname;
	}

	@Override
	public String toString() {
		String str = hostname + "\n";
		for (Snapshot s : snapshots) {
			str += s + "\n\n";
		}
		return str;
	}

}
