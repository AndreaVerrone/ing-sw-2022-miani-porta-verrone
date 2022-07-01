# Software Engineering Project 2021 - 2022
<img src="https://www.craniocreations.it/wp-content/uploads/2021/06/Eriantys_scatola3Dombra-600x600.png" width=400px height=400px style="float: right;"/>

**Group:** PSP31

**Students:**
- [Giorgio Miani](https://github.com/Giorgio-Miani)
- [Alessia Porta](https://github.com/AlessiaPorta)
- [Andrea Verrone](https://github.com/AndreaVerrone)

## Introduction
This is the final test of "**Software Engineering**" course of "**Computer Science Engineering**" held at Politecnico di Milano.

It is a Java versione of the board game "_Eriantys_" made by Cranio Creations.
You can find out more about the game [here](https://craniointernational.com/products/eriantys/) (italian version [here](https://www.craniocreations.it/prodotto/eriantys/)) or download the [english rules](https://craniointernational.com/2021/wp-content/uploads/2021/06/Eriantys_rules_small.pdf) or [italian rules](https://www.craniocreations.it/wp-content/uploads/2021/11/Eriantys_ITA_bassa.pdf).


### Implemented features
| Functionality            | Implemented  |
|--------------------------|:------------:|
| Basic rules              |      🟢      | 
| Complete rules           |      🟢      |
| Socket                   |      🟢      |
| CLI                      |      🟢      |
| GUI                      |      🟢      |
| Multiple games           |      🟢      |
| Disconnection resilience |      🟢      |
| All character cards      |      🟢      |

### Usage

In the `deliverables/jar` folder there are 3 `.jar` files: one for the server,
one for the CLI and one for the GUI. In order to run the jars, clone this repository or download the `.jar` files.

Then, from the command line of your operating system, place inside the folder in which the jars are located and run the following command:

    `java -jar <name-of-jar>.jar`

where `<name-of-jar>` is one of the following:

<table>
<tr>
<th>Name of jar</th>
<th>Description</th>
<th>Command</th>
</tr>
<tr>
<td>PSP31-server</td>
<td>Starts the server</td>
<td>

```bash
java -jar PSP31-server.jar
```
</td> 
</tr>
<tr>
<td>PSP31-cli</td>
<td>Starts the CLI</td>
<td>

```bash
java -jar PSP31-cli.jar
```
</td> 
</tr>
<tr>
<td>PSP31-gui</td>
<td>Starts the GUI</td>
<td>

```bash
java -jar PSP31-gui.jar
```
</td> 
</tr>
</table>