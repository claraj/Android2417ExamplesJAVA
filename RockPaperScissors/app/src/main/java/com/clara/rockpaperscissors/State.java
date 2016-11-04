package com.clara.rockpaperscissors;

/**
 * Created by clara on 11/4/16. What's the state of the game?
 *
 */

public class State {

	/*
	This player can have the following states:

	 - offline
	 - online and seeking opponent
	 - online and matched with opponent
	 - ready
	 - thinking about play			<-------|
	 - decided on play						|
	 - viewing results						|
	 - reset for new game     ---------------


	 After reset, start new game and repeat

	 And their opponent has the same states. Each player has to be in the same state before either may move to the next.
	 This class helps manage what state they are in

	 */


	public final static String OFFLINE = "offline";
	public final static String ONLINE_AND_AVAILABLE = "available";
	public final static String READY_THINKING_ABOUT_PLAY = "ready";
	public final static String DECIDED_PLAY = "decided";
	public final static String VIEWING_RESULTS = "viewing";
	public final static String RESET = "reset";





}
