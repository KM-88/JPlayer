package core.obj;

import java.io.IOException;
import java.nio.file.Path;

import mpatric.mp3agic.ID3v1;
import mpatric.mp3agic.ID3v2;
import mpatric.mp3agic.Mp3File;
import mpatric.mp3agic.tagexecption.InvalidDataException;
import mpatric.mp3agic.tagexecption.UnsupportedTagException;

public class MP3FileSystemObject extends FileSystemObject {

	private Mp3File mp3file;

	public MP3FileSystemObject() {
	}

	public MP3FileSystemObject(Path location) {
		super(location);
		configureTag();
	}

	public MP3FileSystemObject(String name, Path location, FileTypeEnum type) {
		super(name, location, type);
		configureTag();
	}

	public MP3FileSystemObject(String name, Path location, FileTypeEnum type, FileSystemObject parent) {
		super(name, location, type, parent);
		configureTag();
	}

	private void configureTag() {
			try {
				mp3file = new Mp3File(this.getLocationPath().toString());
			} catch (UnsupportedTagException | InvalidDataException | IOException e) {
				e.printStackTrace();
			}
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		if (mp3file.hasId3v1Tag()) {
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
			stringBuilder.append("Track: " + id3v1Tag.getTrack()).append("\t").append("Artist: " + id3v1Tag.getArtist())
					.append("\t").append("Title: " + id3v1Tag.getTitle()).append("\t")
					.append("Album: " + id3v1Tag.getAlbum()).append("\t").append("Year: " + id3v1Tag.getYear())
					.append("\t").append("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")")
					.append("\t").append("Comment: " + id3v1Tag.getComment());
		} else if (mp3file.hasId3v2Tag()) {
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			stringBuilder.append("Track: " + id3v2Tag.getTrack()).append("\t").append("Artist: " + id3v2Tag.getArtist())
					.append("\t").append("Title: " + id3v2Tag.getTitle()).append("\t")
					.append("Album: " + id3v2Tag.getAlbum()).append("\t").append("Year: " + id3v2Tag.getYear())
					.append("\t").append("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")")
					.append("\t").append("Comment: " + id3v2Tag.getComment()).append("\t")
					.append("Composer: " + id3v2Tag.getComposer()).append("\t")
					.append("Publisher: " + id3v2Tag.getPublisher()).append("\t")
					.append("Original artist: " + id3v2Tag.getOriginalArtist()).append("\t")
					.append("Album artist: " + id3v2Tag.getAlbumArtist()).append("\t")
					.append("Copyright: " + id3v2Tag.getCopyright()).append("\t").append("URL: " + id3v2Tag.getUrl())
					.append("\t").append("Encoder: " + id3v2Tag.getEncoder());
			byte[] albumImageData = id3v2Tag.getAlbumImage();
			if (albumImageData != null) {
				stringBuilder.append("\t").append("Have album image data, length: " + albumImageData.length + " bytes")
						.append("\t").append("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
			}
		}
		return stringBuilder.toString();
	}

}
