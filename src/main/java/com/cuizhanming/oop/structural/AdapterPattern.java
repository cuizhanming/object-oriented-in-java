package com.cuizhanming.oop.structural;

/**
 * Adapter Pattern - Allows incompatible interfaces to work together
 * 适配器模式 - 允许不兼容的接口协同工作
 */
public class AdapterPattern {

    // Target interface (what client expects)
    public interface MediaPlayer {
        void play(String audioType, String fileName);
    }

    // Adaptee interfaces (existing incompatible interfaces)
    public interface AdvancedMediaPlayer {
        void playVlc(String fileName);
        void playMp4(String fileName);
    }

    // Concrete adaptees
    public static class VlcPlayer implements AdvancedMediaPlayer {
        @Override
        public void playVlc(String fileName) {
            System.out.println("Playing vlc file: " + fileName);
        }

        @Override
        public void playMp4(String fileName) {
            // VLC player doesn't support mp4
        }
    }

    public static class Mp4Player implements AdvancedMediaPlayer {
        @Override
        public void playVlc(String fileName) {
            // MP4 player doesn't support vlc
        }

        @Override
        public void playMp4(String fileName) {
            System.out.println("Playing mp4 file: " + fileName);
        }
    }

    // Adapter class
    public static class MediaAdapter implements MediaPlayer {
        private final AdvancedMediaPlayer advancedMusicPlayer;

        public MediaAdapter(String audioType) {
            this.advancedMusicPlayer = switch (audioType.toLowerCase()) {
                case "vlc" -> new VlcPlayer();
                case "mp4" -> new Mp4Player();
                default -> throw new IllegalArgumentException("Unsupported audio type: " + audioType);
            };
        }

        @Override
        public void play(String audioType, String fileName) {
            switch (audioType.toLowerCase()) {
                case "vlc" -> advancedMusicPlayer.playVlc(fileName);
                case "mp4" -> advancedMusicPlayer.playMp4(fileName);
                default -> System.out.println("Invalid media. " + audioType + " format not supported");
            }
        }
    }

    // Context (client)
    public static class AudioPlayer implements MediaPlayer {

        @Override
        public void play(String audioType, String fileName) {
            // Built-in support for mp3
            if ("mp3".equalsIgnoreCase(audioType)) {
                System.out.println("Playing mp3 file: " + fileName);
            }
            // Use adapter for other formats
            else if ("vlc".equalsIgnoreCase(audioType) || "mp4".equalsIgnoreCase(audioType)) {
                MediaAdapter mediaAdapter = new MediaAdapter(audioType);
                mediaAdapter.play(audioType, fileName);
            } else {
                System.out.println("Invalid media. " + audioType + " format not supported");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Adapter Pattern Demo ===");

        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "beyond_the_horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far_far_away.vlc");
        audioPlayer.play("avi", "mind_me.avi");
    }
}
