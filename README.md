# SSHelter Client


SSHelter is a simple tool letting you sync across your computers parts of your ssh config like the host machines and specific config for each (like Port Forwarding). It was inspired by the really neat [Termius](https://termius.com/) but since it's a bit pricy for simple hobbyists, I decided to make my own implementation of the features I most needed.

This does not sync your SSH keys yet, as I'm not comfortable to ensure the security required for this.

This is the graphical client for the [SSHelter](https://github.com/oxodao/sshelter) server. Please don't mix&match the client and server version. Be sure to be always on the same version for both. Also, you can find a lighter version CLI only on the [SSHelter-client](https://github.com/oxodao/sshelter-client) repository.

## Installation

@TODO

## Usage

Simply run the jar with Java 11 (It won't work in higher / lower version, because of a [dependency](https://github.com/dorkbox/SystemTray) I'm using that is still not up to date)

The first time, you will be greeted with a prompt to login. Fill the information and you will have an icon in your system tray. When right-clicked, it will give you a list of your machine. Clicking on them will silently connect in SSH to them so that you can access your port forwardings.

## Roadmap
- [ ] Implementing basic auto-sync
- [ ] Implementing a way to create machines
- [ ] Implementing a way to delete machines
- [ ] Implementing a way to export machines
- [ ] SSH Connection to the machines
- [ ] General system stability improvements to enhance the user's experience.

## License
> SSHelter-ui Client - Simple ssh config sync software
> Copyright (C) 2021 - Oxodao
> 
> This program is free software: you can redistribute it and/or modify
> it under the terms of the GNU General Public License as published by
> the Free Software Foundation, either version 3 of the License, or
> (at your option) any later version.
> 
> This program is distributed in the hope that it will be useful,
> but WITHOUT ANY WARRANTY; without even the implied warranty of
> MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
> GNU General Public License for more details.
> 
> You should have received a copy of the GNU General Public License
> along with this program.  If not, see <https://www.gnu.org/licenses/>.

## Contributing

So yeah this is only a simple side project, if you want to contribute by all mean go ahead, no guidelines or so just don't be a jerk. I reserve the right to refuse any PR without explanation or stuff like that. Maybe one day this software will grow in feature-set enough for me to consider it as a **real** project and have a clearer way of handling this stuff.

