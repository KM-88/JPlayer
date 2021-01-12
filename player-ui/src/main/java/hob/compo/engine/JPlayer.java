package hob.compo.engine;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;


public class JPlayer extends AdvancedPlayer {

    private int pausedOnFrame = 0;

    private boolean finished = false, active = false;;

    private static final Object mutex = new Object();

    public JPlayer(InputStream stream) throws JavaLayerException {
        super(stream);
        setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackFinished(PlaybackEvent event) {
                pausedOnFrame = event.getFrame();
            }

            @Override
            public void playbackPaused(PlaybackEvent event) {
                super.playbackPaused(event);
                pausedOnFrame = event.getFrame();
            }
        });
    }

    public void play() {
        while (true) {
            try {
                super.play(pausedOnFrame, Integer.MAX_VALUE);
                active = true;
                System.out.println("Fucking Yield");
                synchronized (mutex) {
                    if (!hasPlayBackFinished()) {
                    	finished = true;
                        Thread.yield();
                        mutex.wait();
                    } else {
                        break;
                    }
                }
            } catch (JavaLayerException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                System.err.println(e.getCause());
                break;
            }
        }
    }

    public void stop() {
        super.stop();
    }

    public void pause() {
        super.pause();
        active = false;
    }

    public void resume() {
        synchronized (mutex) {
            mutex.notify();
        }
    }
}
