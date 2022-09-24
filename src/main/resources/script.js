import { sequences } from "./store.js";

const input = document.getElementById("a");
const output0 = document.getElementsByName("x0");
const output1 = document.getElementsByName("x1");
const output2 = document.getElementsByName("x2");
const output3 = document.getElementsByName("x3");
const batchs = sequences;

input.addEventListener("input", () => {
    const regex = new RegExp(input.value + ".*", 'gi');
    if (regex.toString() == new RegExp(".*", "gi")) {
        output0.item(0).value = "";
        output1.item(0).value = "";
        output2.item(0).value = "";
        output3.item(0).value = "";
    }
    else {
        let buffer = [];
        let result = [];

        batchs.executedSequences.map(sequence => sequence.sequenceName).forEach(name => buffer.push(name));
        buffer.filter(element => {
            return regex.test(element) && element.length < 10;
        }).forEach(element => result.push(element));

        output0.item(0).value = result[0] == undefined ? "" : result[0];
        output1.item(0).value = result[1] == undefined ? "" : result[1];
        output2.item(0).value = result[2] == undefined ? "" : result[2];
        output3.item(0).value = result[3] == undefined ? "" : result[3];
    }
});