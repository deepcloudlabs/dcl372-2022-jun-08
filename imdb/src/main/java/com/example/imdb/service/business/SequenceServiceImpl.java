package com.example.imdb.service.business;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.imdb.service.SequenceService;

//@Component  // Presentation Layer -> REST API
@Service    // Business Layer
//@Repository // Data Access Layer
// Meta-annotation
@Lazy
public class SequenceServiceImpl implements SequenceService {
	private Map<String, AtomicLong> sequences = new ConcurrentHashMap<String, AtomicLong>();

	public SequenceServiceImpl() {
		System.out.println("SequenceServiceImpl::SequenceServiceImpl");
	}

	@Override
	public long nextId(String sequenceName) {
		return getAtomicLong(sequenceName).incrementAndGet();
	}

	@Override
	public String nextId(String sequenceName, String prefix) {
		return prefix + nextId(sequenceName);
	}

	@Override
	public long nextId(String sequenceName, int step) {
		return getAtomicLong(sequenceName).addAndGet(step);
	}

	@Override
	public String nextId(String sequenceName, String prefix, int step) {
		return prefix + nextId(sequenceName, step);
	}

	private AtomicLong getAtomicLong(String sequenceName) {
		AtomicLong atomicLong = sequences.get(sequenceName);
		if (atomicLong == null) {
			atomicLong = new AtomicLong(0);
			sequences.put(sequenceName, atomicLong);
		}
		return atomicLong;
	}
}
