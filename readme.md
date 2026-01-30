<h1>Part 1</h1>
<ol>
    <li>A</li>
    <li>For x==10, the on-point would be x=10, and the off point would be x!=10 because 10==10 is true and !10==10 is false.</li>
    <li>Maybe there should be cases where one of the inputs are valid and the other is not. For example, [1000, 5000] or [C,Z].
        <br>More Specifically: <ul>
            <li>[999,1000]: invalid</li>
            <li>[4000,4001]: invalid</li>
            <li>[B,B]: invalid</li>
            <li>[B,C]: invalid </li>
            <li>[C,C]: valid</li>
            <li>[N,N]: invalid</li>
            <li>[M,N]: invalid</li>
            <li>[M,M]: valid</li>
        </ul>
        These cases take into account the lower and upper on and off points for both the 2 numbers and letters that can be input into the program.</li>
</ol>
<br>
<h1>Part 2</h1>
<p>See src/test/java/NumberUtilsTest.java</p>
<br>
<h1>Part 3</h1>
<p><strong>Does the program have a bug?</strong></p>
<p>Yes, I believe there are two bugs.</p>
<ol>
    <li>The spec never specifies a case if the user is allowed to use the same list for both the right and left numbers. This assumes that the add function should properly add the same list (i.e. same numbers) together properly and return a list 2x that of the input.
    <ul>
        <li><Strong>For Example:</Strong> Assume Integer List number=[1,4], NumberUtils.add(number,number) should return [2,8], but instead returns [8,2]</li>
    </ul></li>
    <li>The spec also only ever mentions "Lists". It never specifies immutable or mutable lists. This suggests that both should properly sum any combination of mutable or immutable lists, however the test fails with the use of immutable lists.</li>
</ol>
<p>The spec must be updated to specify the use of mutable lists only and that both lists can not be the same object, OR the method must support immutable lists and the same object for both params</p>