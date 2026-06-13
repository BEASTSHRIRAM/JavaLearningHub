import os
files = [
    'c:/OOPS/docs/index.html',
    'c:/OOPS/docs/java-basics/index.html',
    'c:/OOPS/docs/oops/index.html',
    'c:/OOPS/docs/dsa/index.html',
    'c:/OOPS/docs/java-backend/index.html',
    'c:/OOPS/docs/spring-ai/index.html'
]
for file_path in files:
    with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
        content = f.read()
    content = content.replace('</div>
            <button class=\"hamburger\" id=\"hamburger\" aria-label=\"Toggle navigation\">?</button>', '</div>\n            <button class=\"hamburger\" id=\"hamburger\" aria-label=\"Toggle navigation\">?</button>')
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)
