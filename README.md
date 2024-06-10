<div align="center">
<h1>YolBI</h1>
<p>
    <img width="200" src="https://avatars.githubusercontent.com/u/159465859?s=200&v=4">
</p>

[Website](https://yapeteam.github.io) |
[Github](https://github.com/yapeteam) 
</div>

YolBI for Fabric is an Minecraft hacked client with fabric API

## License

This project is subject to the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html). This
does only apply for source code located directly in this clean repository. During the development and compilation
process, additional source code may be used to which we have obtained no rights. Such code is not covered by the GPL
license.

For those who are unfamiliar with the license, here is a summary of its main points. This is by no means legal advice
nor legally binding.

*Actions that you are allowed to do:*

- Use
- Share
- Modify

*If you do decide to use ANY code from the source:*

- **You must disclose the source code of your modified work and the source code you took from this project. This means
  you are not allowed to use code from this project (even partially) in a closed-source (or even obfuscated)
  application.**
- **Your modified application must also be licensed under the GPL**

## Setting up a Workspace


1. Clone the repository using `git clone git@github.com:yapeteam/YolBi.git`.
2. CD into the local repository.
3. Run `./gradlew genSources`.
4. Open the folder as a Gradle project in your preferred IDE.
5. Run the client.


### Mixins

Mixins can be used to modify classes at runtime before they are loaded. LiquidBounce uses it to inject its code into the
Minecraft client. This way, none of Mojang's copyrighted code is shipped. If you want to learn more about it, check out
its [Documentation](https://docs.spongepowered.org/5.1.0/en/plugin/internals/mixins.html).


## Stats

![Alt](https://repobeats.axiom.co/api/embed/57baa8bc4c75948f61bff12f35420a0aac9c51ec.svg "Repobeats analytics image")