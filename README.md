# SSHelter Client


SSHelter is a simple tool letting you sync across your computers parts of your ssh config like the host machines and specific config for each (like Port Forwarding). It was inspired by the really neat [Termius](https://termius.com/) but since it's a bit pricy for simple hobbyists, I decided to make my own implementation of the features I most needed.

This does not sync your SSH keys yet, as I'm not comfortable to ensure the security required for this.

This is the graphical client for the [SSHelter](https://github.com/oxodao/sshelter) server. Please don't mix&match the client and server version. Be sure to be always on the same version for both. Also, you can find a lighter version CLI only on the [SSHelter-client](https://github.com/oxodao/sshelter-client) repository.

## Installation

Grab the latest zip from the release tab and extract it in `/opt/sshelter`. Updating is simply a matter or removing this directory and doing it again.

Then, for simple usage, you can just symlink the starting script:
```bash
$ sudo ln -s /opt/sshelter/bin/SSHelter /usr/local/sshelter
```

## Usage

Once installed, you simply have to run the sshelter command. There is no shortcut made yet, so it won't be found in your standard app launcher.

The first time, you will be greeted with a prompt to login. Fill the information, then you will have an icon in your system tray. When right-clicked, it will give you a list of your machine. In the future, clicking on them will silently connect in SSH to them so that you can access your port forwardings.

## Roadmap
- [x] Implementing basic auto-sync
- [x] Implementing a way to create machines
- [x] Implementing a way to delete machines
- [ ] Implementing a way to export machines
- [ ] Fix the API => editing a machine that does not change its shortname results in an error
- [ ] Handle HiDPI properly
- [ ] SSH Connection to the machines
    - [ ] Let the user choose which SSH key to use (client-side only)
    - [ ] CLI using jediterm
    - [ ] Have a global state about each SSH connection to use the same every time
    - [ ] File transfer
- [ ] Use home made icons
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

