package com.better.pattern.compound._mvc.model;

import com.better.pattern.compound._mvc.observer.BMPObserver;
import com.better.pattern.compound._mvc.observer.BeatObserver;
import com.better.pattern.iterator.Iterator;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhaoyu on 2017/1/8.
 */
public class BeatModel implements BeatModelInterface, MetaEventListener {

    Sequencer sequencer;
    List<BeatObserver> beatObservers = new ArrayList<>();
    List<BMPObserver> bmpObservers = new ArrayList<>();
    int bmp = 90;
    Sequence sequence;
    Track track;


    @Override
    public void init() {
        setupMidi();
        buildTrackAndStart();
    }

    private void buildTrackAndStart() {
        int[] trackList = {35, 0, 46, 0};
        sequence.deleteTrack(null);
        track = sequence.createTrack();

        makeTracks(trackList);
        track.add(makeEvent(192, 9, 1, 0, 4));
        try {
            sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private void makeTracks(int[] trackList) {
        for (int i = 0; i < trackList.length; i++) {
            int key = trackList[i];

            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    private MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;

        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }


        return event;
    }

    private void setupMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);

            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(getBMP());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void on() {
        sequencer.start();
        setBMP(90);
    }

    @Override
    public void off() {
        setBMP(0);
        sequencer.stop();
    }

    @Override
    public void setBMP(int bmp) {
        this.bmp = bmp;
        sequencer.setTempoInBPM(getBMP());
        notifyBMPObservers();
    }

    @Override
    public int getBMP() {
        return bmp;
    }

    @Override
    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    @Override
    public void removeObserver(BeatObserver o) {
        if (beatObservers.contains(o)) {
            beatObservers.remove(o);
        }
    }

    @Override
    public void registerObserver(BMPObserver o) {
        bmpObservers.add(o);
    }

    @Override
    public void removeObserver(BMPObserver o) {
        if (bmpObservers.contains(o)) {
            bmpObservers.remove(o);
        }
    }

    @Override
    public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {
            beatEvent();
            sequencer.start();
            setBMP(getBMP());
        }
    }

    private void beatEvent() {
        notifyBeatsObservers();
    }

    private void notifyBeatsObservers() {
        for (BMPObserver o : bmpObservers) {
            o.updateBMP();
        }
    }

    private void notifyBMPObservers() {
        for (BeatObserver o : beatObservers) {
            o.updateBeat();
        }
    }
}
