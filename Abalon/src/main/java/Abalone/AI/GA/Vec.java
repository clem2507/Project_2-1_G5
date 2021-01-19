package Abalone.AI.GA;

import java.lang.*;

/** 
 * This is the helper class that provides all necessary tools for geometrical vectors manipulation
 * Has the same structure throughout 
 */
public class Vec {

	public double[] v;
	public int length = 0;

	public Vec() {

	}

	public Vec(int len) {
		v = new double[len];
		length = len;
	}

	public Vec(double[] _v) {
		v = new double[_v.length];
		for (int i = 0; i < v.length; i++)
			v[i] = _v[i];

		length = v.length;
	}

	public Vec(Vec another) {
		v = new double[another.v.length];
		length = another.v.length;

		for (int i = 0; i < v.length; i++)
			v[i] = another.v[i];
	}

	public static Vec add(Vec l, Vec r) {
		Vec res = new Vec(l.length);	
		for (int i = 0; i < l.length; i++)
			res.v[i] = l.v[i] + r.v[i];
		return res;
	}

	public static Vec substract(Vec l, Vec r) {
		Vec res = new Vec(l.length);
		for (int i = 0; i < l.length; i++)
			res.v[i] = l.v[i] - r.v[i];
		return res;
	}

	public static double multiply(Vec l, Vec r) {
		double res = 0;
		for (int i = 0; i < l.length; i++)
			res += l.v[i] * r.v[i];
		return res;
	}

	public static Vec multiply(Vec l, double r) {
		Vec res = new Vec(l.length);
		for (int i = 0; i < l.length; i++) 
			res.v[i] = l.v[i] * r;
		return res;
	}

	public static Vec divide(Vec l, double r) {
		Vec res = new Vec(l.length);
		for (int i = 0; i < l.length; i++) 
			res.v[i] = l.v[i] / r;
		return res;
	}

	public double size() {
		double res = 0;
		for (int i = 0; i < length; i++) 
			res += v[i] * v[i];
		return Math.sqrt(res);
	}

	public void normalize() {
		double len = size();
		for (int i = 0; i < length; i++) 
			v[i] /= len;
	}

	public double[] toDouble() {
		double[] new_vec = new double[v.length];
		for (int i = 0; i < v.length; i++)
			new_vec[i] = v[i];
		return new_vec;
	}

	public boolean equals(Object otherObject) {
		if (otherObject == null)
			return false;
		if (otherObject.getClass() != getClass())
			return false;
		Vec r = (Vec)otherObject;
		return (length == r.length && v == r.v);
	}
}
