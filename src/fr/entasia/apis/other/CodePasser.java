package fr.entasia.apis.other;

public class CodePasser <Arg, Res> {

	public static abstract class Both<Arg, Res> {
		public abstract Res run(Arg arg);
	}

	public static abstract class Arg<Arg> {

		public abstract void run(Arg arg);

	}

	public static abstract class Res<Res> {
		public abstract Res run();
	}

	public static abstract class None {
		public abstract void run();
	}

}
