function driver() {
    analyzeContent("This\nIs\nA\nLot\nOf\nNew\nLines!");
    analyzeContent("body{this is a html body} a{color:#fef0} a{padding:100} c{color:#fff}");
    analyzeContent("<html><head></head></html><div><p>Lots of text</p></div><p>Closing text</p><html></html>");
}

function textObj(str) {
    let temp = str;
    let totalLines = 1;

    while (temp.indexOf("\n") != -1) {
        temp = temp.substring(0, temp.lastIndexOf("\n"));
        totalLines += 1;
    }

    const text = {};
    text.contentType = "TEXT";
    text.lineNumber = totalLines;

    return text;
}


function cssObj(words) {
    let i = 0;
    for (const element of words) {
        temp = element.substring(0, element.indexOf("{"));
        words[i] = temp;
        i += 1;
    }

    let recurring = findRecurring(words);

    const css = {};
    css.contentType = "CSS";
    css.cssTargets = recurring;

    return css;
}

function htmlObj(words) {
    let i = 0;
    for (const element of words) {
        temp = element.substring(element.indexOf("<") + 1, element.indexOf(">"));
        words[i] = temp;
        i += 1;
    }

    let recurring = findRecurring(words);

    const html = {};
    html.contentType = "HTML";
    html.tags = recurring;

    return html;
}

function findRecurring(words) {
    let recurring = {};

    for (const word of words) {
        if (recurring[word]) {
            recurring[word]++;
        } else {
            recurring[word] = 1;
        }
    }

    return recurring;
}

function analyzeContent(str) {
    let cssPattern = /[a-zA-Z0-9_]+{[^}]*}/g
    let htmlPattern = /<[a-zA-Z0-9_]*>/g

    let words;

    if (words = str.match(cssPattern)) {
        console.log(cssObj(words));
    } else if (words = str.match(htmlPattern)) {
        console.log(htmlObj(words));
    } else {
        console.log(textObj(str));
    }
}

driver();
