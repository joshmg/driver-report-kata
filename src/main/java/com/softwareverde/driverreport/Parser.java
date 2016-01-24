package com.softwareverde.driverreport;

public class Parser {
    final String _spaceDeliminator = " ";
    final String _newlineDeliminator = "\n";
    String _content = "";
    Integer _position = 0;

    // NOTE: Returns EOF if not found...
    private Integer _getNextSpaceIndex() {
        final Integer index = _content.indexOf(_spaceDeliminator, _position);

        if (index < 0) {
            return _content.length();
        }

        return index;
    }

    // NOTE: Returns EOF if not found...
    private Integer _getNextNewlineIndex() {
        final Integer index = _content.indexOf(_newlineDeliminator, _position);

        if (index < 0) {
            return _content.length();
        }

        return index;
    }

    private Integer _getNextDelimiterIndex() {
        final Integer spaceIndex = _getNextSpaceIndex();
        final Integer newlineIndex = _getNextNewlineIndex();

        return (spaceIndex < newlineIndex ? spaceIndex : newlineIndex);
    }

    public void setContent(String content) {
        _content = content;
        _position = 0;
    }

    public String getNextWord() {
        if (_position >= _content.length()) {
            return "";
        }

        if (! _content.contains(_spaceDeliminator)) {
            String nextWord = _content.substring(_position);
            _position += nextWord.length();
            return nextWord;
        }

        Integer endIndex = _getNextDelimiterIndex();
        if (endIndex < 0) {
            endIndex = _content.length() - 1;
        }

        String nextWord = _content.substring(_position, endIndex);
        _position += nextWord.length() +1;
        return nextWord;
    }

    public void reset() {
        _position = 0;
    }
}
