package polygonull.noskulls.components;

import java.util.ArrayList;

import polygonull.noskulls.helpers.AlphanumericHelper;

public class GameTicker {

	private int tickerWidth;
	private int alphanumericWidth;
	private String level;
	private String best;
	private ArrayList<String> messages;

	private AlphanumericHelper alphanumericHelper;

	private int messageIterator;
	private boolean messageEntering;
	private int animationSpeed;

	private ArrayList<Integer> exitingMessageOffsets;
    private int exitingMessagePosition;
    private int exitingMessageWidth;

	private ArrayList<Integer> enteringMessageOffsets;
	private int enteringMessagePosition;
	private int enteringMessageWidth;

    public GameTicker(int tickerWidth, int alphanumericWidth, AlphanumericHelper alphanumericHelper, String level, String best, ArrayList<String> messages) {
    	this.tickerWidth = tickerWidth;
    	this.alphanumericWidth = alphanumericWidth;
		this.alphanumericHelper = alphanumericHelper;
    	this.level = level;
    	this.best = best;
    	this.messages = messages;

    	messageIterator = 0;
    	messageEntering = false;
    	animationSpeed = 3;

    	String message = getNextMessage();

		exitingMessageOffsets = alphanumericHelper.stringToOffsets(message);
    	exitingMessagePosition = tickerWidth/2 - 1;
    	exitingMessageWidth = calcMessageWidth(message);
	}

	private int calcMessageWidth(String message) {
    	return alphanumericWidth * message.length();
    }

	private String getNextMessage() {
		if(messageIterator == messages.size()) {
			messageIterator = 0;
		}
		return messages.get(messageIterator++);
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setBest(String best) {
    	this.best = best;
    }

	public void addMessage(String message) {
    	this.messages.add(message);
	}

	public void removeLastMessage() {
    	if(messages.size() > 0) messages.remove(messages.size() - 1);
    }

	public int getExitingMessageOffsetsSize() {
    	return exitingMessageOffsets.size();
	}

	public int getExitingMessageOffset(int position) {
    	if(position < exitingMessageOffsets.size()) {
    		return exitingMessageOffsets.get(position);
		} else {
    		return -1;
		}
	}

    public int getExitingMessagePosition() {
    	return exitingMessagePosition;
	}

	public int getEnteringMessageOffsetsSize() {
		return enteringMessageOffsets.size();
	}

	public int getEnteringMessageOffset(int position) {
		if(position < enteringMessageOffsets.size()) {
			return enteringMessageOffsets.get(position);
		} else {
			return -1;
		}
	}

	public int getEnteringMessagePosition() {
		return enteringMessagePosition;
	}

	public boolean isMessageEntering() {
		return messageEntering;
	}

	public void update() {
		if (exitingMessagePosition < -exitingMessageWidth) {
			exitingMessageOffsets = enteringMessageOffsets;
			exitingMessagePosition = enteringMessagePosition;
			exitingMessageWidth = enteringMessageWidth;
			enteringMessageOffsets = null;
			enteringMessagePosition = -1;
			enteringMessageWidth = -1;
			messageEntering = false;
		} else if(exitingMessagePosition < -(exitingMessageWidth - tickerWidth / 2) && !messageEntering) {
			String message = getNextMessage();
			enteringMessageOffsets = alphanumericHelper.stringToOffsets(message);
			enteringMessagePosition = tickerWidth;
			enteringMessageWidth = alphanumericWidth * message.length();
			messageEntering = true;
		}
		exitingMessagePosition -= animationSpeed;
		enteringMessagePosition -= animationSpeed;
    }

}
