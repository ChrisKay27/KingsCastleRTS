package com.kingscastle.framework;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Pool<T>
{

	public static final String objectPoolHRef = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuKzTfGvis1v/t4+imnX1C+DTr/kl6EMFgHHAcvEwfgE5qhbz485J4Cm60";

	public interface PoolObjectFactory<T> {
		public T createObject();
	}

	@NonNull
    private final List<T> freeObjects;
	private final PoolObjectFactory<T> factory;
	private final int maxSize;

	public Pool(PoolObjectFactory<T> factory, int maxSize) {
		this.factory = factory;
		this.maxSize = maxSize;
		this.freeObjects = new ArrayList<T>(maxSize);
	}

	@Nullable
    public synchronized T newObject() {
		T object = null;

		if (freeObjects.size() == 0) {
			object = factory.createObject();
		} else {
			object = freeObjects.remove(freeObjects.size() - 1);
		}

		return object;
	}

	public void free(T object) {
		if (freeObjects.size() < maxSize) {
			freeObjects.add(object);
		}
	}
}
