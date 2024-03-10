// CREDIT: ChatGPT.  Had list of captured pieces, but wasn't sure on how to bridge the gap between classes in implementation.
package main.java;

/**
 * Interface defining a listener for piece capture events.
 * Used to respond to a piece capture, at which point the method to update the captured piece list is triggered.
 */

public interface OnPieceCapturedListener {
    /**
     * Called when piece is captured.
     * @param piece The piece that is being captured.
     */
    void onPieceCaptured(Piece piece);
}