package com.ebay.sell.inventory.inventoryitems.models.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.eclipse.persistence.oxm.annotations.XmlVariableNode;

public class AspectsAdapter extends XmlAdapter<AspectsAdapter.AdaptedMap, Map<String, List<String>>> {

	public static class AdaptedMap {

		@XmlVariableNode("key")
		List<AdaptedEntry> entries = new ArrayList<AdaptedEntry>();

	}

	public static class AdaptedEntry {

		@XmlTransient
		public String key;

		@XmlValue
		public String value;

	}

	@Override
	public AdaptedMap marshal(Map<String, List<String>> map) throws Exception {
		AdaptedMap adaptedMap = new AdaptedMap();
		for (Entry<String, List<String>> entry : map.entrySet()) {
			for (String value : entry.getValue()) {
				AdaptedEntry adaptedEntry = new AdaptedEntry();
				adaptedEntry.key = entry.getKey();
				adaptedEntry.value = value;
				adaptedMap.entries.add(adaptedEntry);
			}
		}
		return adaptedMap;
	}

	@Override
	public Map<String, List<String>> unmarshal(AdaptedMap adaptedMap) throws Exception {
		List<AdaptedEntry> adaptedEntries = adaptedMap.entries;
		Map<String, List<String>> map = new HashMap<>(adaptedEntries.size());
		for (AdaptedEntry adaptedEntry : adaptedEntries) {
			if (map.containsKey(adaptedEntry.key)) {
				final List<String> list = map.get(adaptedEntry.key);
				list.add(adaptedEntry.value);
			} else {
				final List<String> list = new LinkedList<>();
				list.add(adaptedEntry.value);
				map.put(adaptedEntry.key, list);
			}
		}
		return map;
	}

}