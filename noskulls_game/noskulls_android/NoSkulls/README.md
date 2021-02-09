# Re-building for web

To build the HTML version run:

```
./gradlew html:dist
cp html/build/dist/html/*cache.js ../../noskulls_site/html/
cp html/build/dist/html/compilation-mappings.txt ../../noskulls_site/html/
rm -rf html/build/dist/assets/com
cp -r html/build/dist/assets/* ../../noskulls_site/assets
```

`* More things may need updating, including logo, css, etc.`