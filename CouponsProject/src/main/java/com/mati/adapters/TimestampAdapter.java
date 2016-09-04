package com.mati.adapters;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimestampAdapter extends XmlAdapter<Date, Timestamp>{
	// marshal method receive sql.timestamp and transfer it into java.util.Date
	// which have default ctor and can be used with jackson
	@Override
	public Date marshal(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	// unmarshal do the exact opposite from marshal method
	// it takes java.util.date and turns it into sql.timestamp
	// which can be used by our database
	@Override
	public Timestamp unmarshal(Date date) {
		return new Timestamp(date.getTime());
	}
}
