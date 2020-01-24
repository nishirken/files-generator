#### Generates set of files:
1) the main file
2) interface file
3) the mock file and folder

##### Some examples:
<pre>
files-generator --path ~/test --name File --postfix State
</pre>
The same:
<pre>
files-generator -p ~/test -n File --postfix State
</pre>
The result:
<pre>
ls ~/test
FileState.ts FileState.test.ts  IFileState.ts __mocks__
</pre>
Without postfix and path, so path will be replaced by cwd
<pre>files-generator -n File</pre>
Without tests and path, so path will be replaced by cwd
<pre>files-generator -n File --without-test</pre>
<pre>files-generator -n File -wt</pre>

You can set an alias and save it
<pre>files-generator --set-alias sr Store</pre>
<pre>files-generator -p ~/test -n File -sr</pre>
<pre>
ls ~/test
FileStore.ts FileStore.test.ts IFileStore.ts __mocks__
</pre>

#### Install
<pre>
wget -O - https://github.com/nishirken/files-generator/raw/master/setup.sh | bash
</pre>
