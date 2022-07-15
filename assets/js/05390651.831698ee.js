"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[3232],{3905:function(a,e,l){l.d(e,{Zo:function(){return o},kt:function(){return d}});var t=l(7294);function n(a,e,l){return e in a?Object.defineProperty(a,e,{value:l,enumerable:!0,configurable:!0,writable:!0}):a[e]=l,a}function i(a,e){var l=Object.keys(a);if(Object.getOwnPropertySymbols){var t=Object.getOwnPropertySymbols(a);e&&(t=t.filter((function(e){return Object.getOwnPropertyDescriptor(a,e).enumerable}))),l.push.apply(l,t)}return l}function s(a){for(var e=1;e<arguments.length;e++){var l=null!=arguments[e]?arguments[e]:{};e%2?i(Object(l),!0).forEach((function(e){n(a,e,l[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(a,Object.getOwnPropertyDescriptors(l)):i(Object(l)).forEach((function(e){Object.defineProperty(a,e,Object.getOwnPropertyDescriptor(l,e))}))}return a}function c(a,e){if(null==a)return{};var l,t,n=function(a,e){if(null==a)return{};var l,t,n={},i=Object.keys(a);for(t=0;t<i.length;t++)l=i[t],e.indexOf(l)>=0||(n[l]=a[l]);return n}(a,e);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(a);for(t=0;t<i.length;t++)l=i[t],e.indexOf(l)>=0||Object.prototype.propertyIsEnumerable.call(a,l)&&(n[l]=a[l])}return n}var r=t.createContext({}),u=function(a){var e=t.useContext(r),l=e;return a&&(l="function"==typeof a?a(e):s(s({},e),a)),l},o=function(a){var e=u(a.components);return t.createElement(r.Provider,{value:e},a.children)},p={inlineCode:"code",wrapper:function(a){var e=a.children;return t.createElement(t.Fragment,{},e)}},m=t.forwardRef((function(a,e){var l=a.components,n=a.mdxType,i=a.originalType,r=a.parentName,o=c(a,["components","mdxType","originalType","parentName"]),m=u(l),d=n,h=m["".concat(r,".").concat(d)]||m[d]||p[d]||i;return l?t.createElement(h,s(s({ref:e},o),{},{components:l})):t.createElement(h,s({ref:e},o))}));function d(a,e){var l=arguments,n=e&&e.mdxType;if("string"==typeof a||n){var i=l.length,s=new Array(i);s[0]=m;var c={};for(var r in e)hasOwnProperty.call(e,r)&&(c[r]=e[r]);c.originalType=a,c.mdxType="string"==typeof a?a:n,s[1]=c;for(var u=2;u<i;u++)s[u]=l[u];return t.createElement.apply(null,s)}return t.createElement.apply(null,l)}m.displayName="MDXCreateElement"},2360:function(a,e,l){l.d(e,{Z:function(){return s}});var t=l(7294),n=l(6010),i="tabItem_OmH5";function s(a){var e=a.children,l=a.hidden,s=a.className;return t.createElement("div",{role:"tabpanel",className:(0,n.Z)(i,s),hidden:l},e)}},9877:function(a,e,l){l.d(e,{Z:function(){return d}});var t=l(3117),n=l(7294),i=l(2389),s=l(7392),c=l(7094),r=l(2466),u=l(6010),o="tabList_uSqn",p="tabItem_LplD";function m(a){var e,l,i,m=a.lazy,d=a.block,h=a.defaultValue,b=a.values,k=a.groupId,v=a.className,g=n.Children.map(a.children,(function(a){if((0,n.isValidElement)(a)&&void 0!==a.props.value)return a;throw new Error("Docusaurus error: Bad <Tabs> child <"+("string"==typeof a.type?a.type:a.type.name)+'>: all children of the <Tabs> component should be <TabItem>, and every <TabItem> should have a unique "value" prop.')})),f=null!=b?b:g.map((function(a){var e=a.props;return{value:e.value,label:e.label,attributes:e.attributes}})),w=(0,s.l)(f,(function(a,e){return a.value===e.value}));if(w.length>0)throw new Error('Docusaurus error: Duplicate values "'+w.map((function(a){return a.value})).join(", ")+'" found in <Tabs>. Every value needs to be unique.');var N=null===h?h:null!=(e=null!=h?h:null==(l=g.find((function(a){return a.props.default})))?void 0:l.props.value)?e:null==(i=g[0])?void 0:i.props.value;if(null!==N&&!f.some((function(a){return a.value===N})))throw new Error('Docusaurus error: The <Tabs> has a defaultValue "'+N+'" but none of its children has the corresponding value. Available values are: '+f.map((function(a){return a.value})).join(", ")+". If you intend to show no default tab, use defaultValue={null} instead.");var y=(0,c.U)(),T=y.tabGroupChoices,x=y.setTabGroupChoices,I=(0,n.useState)(N),Z=I[0],S=I[1],C=[],E=(0,r.o5)().blockElementScrollPositionUntilNextRender;if(null!=k){var L=T[k];null!=L&&L!==Z&&f.some((function(a){return a.value===L}))&&S(L)}var O=function(a){var e=a.currentTarget,l=C.indexOf(e),t=f[l].value;t!==Z&&(E(e),S(t),null!=k&&x(k,t))},V=function(a){var e,l=null;switch(a.key){case"ArrowRight":var t=C.indexOf(a.currentTarget)+1;l=C[t]||C[0];break;case"ArrowLeft":var n=C.indexOf(a.currentTarget)-1;l=C[n]||C[C.length-1]}null==(e=l)||e.focus()};return n.createElement("div",{className:(0,u.Z)("tabs-container",o)},n.createElement("ul",{role:"tablist","aria-orientation":"horizontal",className:(0,u.Z)("tabs",{"tabs--block":d},v)},f.map((function(a){var e=a.value,l=a.label,i=a.attributes;return n.createElement("li",(0,t.Z)({role:"tab",tabIndex:Z===e?0:-1,"aria-selected":Z===e,key:e,ref:function(a){return C.push(a)},onKeyDown:V,onFocus:O,onClick:O},i,{className:(0,u.Z)("tabs__item",p,null==i?void 0:i.className,{"tabs__item--active":Z===e})}),null!=l?l:e)}))),m?(0,n.cloneElement)(g.filter((function(a){return a.props.value===Z}))[0],{className:"margin-top--md"}):n.createElement("div",{className:"margin-top--md"},g.map((function(a,e){return(0,n.cloneElement)(a,{key:e,hidden:a.props.value!==Z})}))))}function d(a){var e=(0,i.Z)();return n.createElement(m,(0,t.Z)({key:String(e)},a))}},3518:function(a,e,l){l.d(e,{Z:function(){return u}});var t=l(7294),n=l(9877),i=l(2360),s=l(2389);function c(a){var e=a.children,l=a.fallback;return(0,s.Z)()?t.createElement(t.Fragment,null,null==e?void 0:e()):null!=l?l:null}var r=l(1417);function u(a){return t.createElement(c,null,(function(){return t.createElement("div",null,t.createElement(n.Z,{groupId:"operating-systems",defaultValue:(0,r.l)(),values:[{label:"Windows",value:"windows"},{label:"macOS",value:"mac"},{label:"Linux",value:"linux"},{label:"GitHub Actions",value:"gha"}]},t.createElement(i.Z,{value:"windows"},t.createElement("a",{className:"no_monospace",href:"https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-pc-win32.msi"},"Download Scala CLI for Windows")),t.createElement(i.Z,{value:"linux"},t.createElement("p",null,"Run the following one-line command in your terminal:"),t.createElement("code",null,"curl -sSLf https://virtuslab.github.io/scala-cli-packages/scala-setup.sh | sh")),t.createElement(i.Z,{value:"mac"},t.createElement("p",null,"Run the following one-line command in your terminal:"),t.createElement("code",null,"brew install Virtuslab/scala-cli/scala-cli")),t.createElement(i.Z,{value:"gha"},t.createElement("p",null,"Add the ",t.createElement("a",{href:"https://github.com/VirtusLab/scala-cli-setup"},"scala-cli-setup")," action to your workflow:"),t.createElement("code",null,"steps:",t.createElement("br",null),"\xa0\xa0\xa0\xa0- uses: coursier/cache-action@v6",t.createElement("br",null),"\xa0\xa0\xa0\xa0- uses: VirtusLab/scala-cli-setup@main",t.createElement("br",null)))))}))}},1434:function(a,e,l){l.d(e,{Z:function(){return n}});var t=l(7294);function n(a){return t.createElement("div",{className:"col col--"+a.colsize},t.createElement("h1",{className:"section-title"+(a.promptsign?" with-before":"")},a.title))}},9882:function(a,e,l){l.d(e,{Z:function(){return n}});var t=l(7294);function n(a){return t.createElement("section",{className:"section "+a.className},a.children)}},115:function(a,e,l){l.d(e,{Z:function(){return n}});var t=l(7294);function n(a){var e=a.title.toLowerCase().split(" ").join("-"),l=t.createElement("a",{href:"#"+e},">_");return t.createElement("div",{className:"section-about__wrapper row",id:e},t.createElement("div",{className:"col col--1 big-title pre-title"},l),t.createElement("div",{className:"col col--3 big-title"},t.createElement("span",{className:"pre-title-mobile"},l)," ",a.title),t.createElement("div",{className:"col col--8 description"},a.children))}},1417:function(a,e,l){l.d(e,{l:function(){return t}});var t=function(){var a=function(a){return"undefined"!=typeof window&&-1!==window.navigator.userAgent.indexOf(a)};return a("Win")?"windows":a("Mac")?"mac":"linux"}},4916:function(a,e,l){l.r(e),l.d(e,{default:function(){return w}});var t=l(7294),n=l(7422),i=l(9882),s=l(1434),c=l(3518),r=l(3117),u=l(102),o=l(3905),p=l(9877),m=l(2360),d=l(115);var h=l(1073),b=function(a){function e(e){var l;return(l=a.call(this,e)||this).handleClick=l.handleClick.bind(function(a){if(void 0===a)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return a}(l)),l}(0,h.Z)(e,a);var l=e.prototype;return l.handleClick=function(a){window.location.href=this.props.href},l.render=function(){return t.createElement("button",{class:"button button--danger button--outline",onClick:this.handleClick},this.props.desc)},e}(t.Component),k=l(1417),v=["components"],g={toc:[]};function f(a){var e=a.components,l=(0,u.Z)(a,v);return(0,o.kt)("wrapper",(0,r.Z)({},g,l,{components:e,mdxType:"MDXLayout"}),(0,o.kt)(d.Z,{title:"Advanced Installation",mdxType:"SectionAbout"},(0,o.kt)("div",{className:"margin--lg"}),(0,o.kt)(p.Z,{groupId:"operating-systems-specific",defaultValue:(0,k.l)(),values:[{label:"Windows",value:"windows"},{label:"MacOs",value:"mac"},{label:"Linux",value:"linux"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"windows",mdxType:"TabItem"}),(0,o.kt)(m.Z,{value:"mac",mdxType:"TabItem"}),(0,o.kt)(m.Z,{value:"linux",mdxType:"TabItem"}))),(0,o.kt)("div",{className:"row"},(0,o.kt)("div",{className:"col col--9 col--offset-1 padding--lg advanced_install_methods"},(0,o.kt)("p",null,"Pick the installation method:"),(0,o.kt)(p.Z,{groupId:"operating-systems-specific",defaultValue:(0,k.l)(),values:[{label:"Windows",value:"windows"},{label:"MacOs",value:"mac"},{label:"Linux",value:"linux"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"linux",mdxType:"TabItem"},(0,o.kt)(p.Z,{groupId:"linux",defaultValue:"manual",values:[{label:"Manual",value:"manual"},{label:"Apt",value:"apt"},{label:"Deb",value:"deb"},{label:"Yum",value:"yum"},{label:"Rpm",value:"rpm"},{label:"Alpine",value:"alpine"},{label:"Nix",value:"nix"},{label:"SDKMAN",value:"sdkman"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"manual",mdxType:"TabItem"},(0,o.kt)("p",null,"Download the launcher from GitHub release assets with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"curl -fL https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-pc-linux.gz | gzip -d > scala-cli\nchmod +x scala-cli\nsudo mv scala-cli /usr/local/bin/scala-cli\n")),(0,o.kt)("p",null,"Check that it runs fine by running its ",(0,o.kt)("inlineCode",{parentName:"p"},"about")," command:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli about\n"))),(0,o.kt)(m.Z,{value:"apt",mdxType:"TabItem"},(0,o.kt)("p",null,"Scala CLI can be installed via ",(0,o.kt)("a",{parentName:"p",href:"https://wiki.debian.org/Apt"},"apt")," packager tool."),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},'curl -s --compressed "https://virtuslab.github.io/scala-cli-packages/KEY.gpg" | sudo apt-key add -\nsudo curl -s --compressed -o /etc/apt/sources.list.d/scala_cli_packages.list "https://virtuslab.github.io/scala-cli-packages/debian/scala_cli_packages.list"\nsudo apt update\nsudo apt install scala-cli\n'))),(0,o.kt)(m.Z,{value:"deb",mdxType:"TabItem"},(0,o.kt)("p",null,"The Debian package can be downloaded at ",(0,o.kt)("a",{parentName:"p",href:"https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-pc-linux.deb"},"this address"),"."),(0,o.kt)("p",null,"Alternatively, you can download it and install it manually with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"curl -fLo scala-cli.deb https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-pc-linux.deb\nsudo dpkg -i scala-cli.deb\n"))),(0,o.kt)(m.Z,{value:"yum",mdxType:"TabItem"},(0,o.kt)("p",null,"Scala CLI can be installed via ",(0,o.kt)("a",{parentName:"p",href:"http://yum.baseurl.org"},"yum")," packager tool."),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"sudo cat > /etc/yum.repos.d/virtuslab.repo << EOF\n[virtuslab-repo]\nname=VirtusLab Repo\nbaseurl=https://virtuslab.github.io/scala-cli-packages/CentOS/Packages\nenabled=1\ngpgcheck=1\ngpgkey=https://virtuslab.github.io/scala-cli-packages/KEY.gpg\nEOF\nsudo yum repo-pkgs virtuslab-repo list\nsudo yum install scala-cli\n"))),(0,o.kt)(m.Z,{value:"rpm",mdxType:"TabItem"},(0,o.kt)("p",null,"The RPM package can be downloaded at ",(0,o.kt)("a",{parentName:"p",href:"https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-pc-linux.rpm"},"this address"),"."),(0,o.kt)("p",null,"Alternatively, you can download it and install it manually with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"curl -fLo scala-cli.rpm https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-pc-linux.rpm\nsudo rpm -i scala-cli.rpm\n"))),(0,o.kt)(m.Z,{value:"alpine",mdxType:"TabItem"},(0,o.kt)("p",null,"Download the launcher from GitHub release assets with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"wget -q -O scala-cli.gz  https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-pc-linux-static.gz && gunzip scala-cli.gz\nchmod +x scala-cli\nmv scala-cli /usr/bin/\n")),(0,o.kt)("p",null,"Check that it runs fine by running its ",(0,o.kt)("inlineCode",{parentName:"p"},"about")," command:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli about\n"))),(0,o.kt)(m.Z,{value:"nix",mdxType:"TabItem"},(0,o.kt)("div",{className:"admonition admonition-info alert alert--info"},(0,o.kt)("div",{parentName:"div",className:"admonition-heading"},(0,o.kt)("h5",{parentName:"div"},(0,o.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,o.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"14",height:"16",viewBox:"0 0 14 16"},(0,o.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M7 2.3c3.14 0 5.7 2.56 5.7 5.7s-2.56 5.7-5.7 5.7A5.71 5.71 0 0 1 1.3 8c0-3.14 2.56-5.7 5.7-5.7zM7 1C3.14 1 0 4.14 0 8s3.14 7 7 7 7-3.14 7-7-3.14-7-7-7zm1 3H6v5h2V4zm0 6H6v2h2v-2z"}))),"info")),(0,o.kt)("div",{parentName:"div",className:"admonition-content"},(0,o.kt)("p",{parentName:"div"},"This method is provided and supported by the community, not the core team of Scala CLI"))),(0,o.kt)("p",null,"Scala CLI can be installed with ",(0,o.kt)("a",{parentName:"p",href:"https://nixos.org"},"Nix")," with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"nix-env -iA scala-cli\n"))),(0,o.kt)(m.Z,{value:"sdkman",mdxType:"TabItem"},(0,o.kt)("p",null,"Scala CLI can be installed via ",(0,o.kt)("a",{parentName:"p",href:"https://sdkman.io"},"SDKMAN")," with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"sdk install scalacli\n"))))),(0,o.kt)(m.Z,{value:"windows",mdxType:"TabItem"},(0,o.kt)(p.Z,{groupId:"windows",defaultValue:"manual",values:[{label:"Manual",value:"manual"},{label:"Installer",value:"installer"},{label:"SDKMAN",value:"sdkman"},{label:"Chocolatey",value:"choco"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"manual",mdxType:"TabItem"},(0,o.kt)("p",null,"Note that the Windows manual installation requires ",(0,o.kt)("a",{parentName:"p",href:"https://support.microsoft.com/en-us/topic/the-latest-supported-visual-c-downloads-2647da03-1eea-4433-9aff-95f26a218cc0"},"Visual C++ redistributable"),"\nto be installed. See below for how to install it."),(0,o.kt)("p",null,"Download the launcher from GitHub release assets with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"curl -fLo scala-cli.zip https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-pc-win32.zip\ntar -xf scala-cli.zip\n")),(0,o.kt)("p",null,"Check that it runs fine by running its ",(0,o.kt)("inlineCode",{parentName:"p"},"about")," command:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli about\n")),(0,o.kt)("p",null,"If you get an error about ",(0,o.kt)("inlineCode",{parentName:"p"},"MSVCR100.dll")," being missing, you have to install\n",(0,o.kt)("a",{parentName:"p",href:"https://support.microsoft.com/en-us/topic/the-latest-supported-visual-c-downloads-2647da03-1eea-4433-9aff-95f26a218cc0"},"Visual C++ redistributable"),". A valid version is distributed with the Scala CLI launchers.\nYou can download it ",(0,o.kt)("a",{parentName:"p",href:"https://github.com/Virtuslab/scala-cli/releases/latest/download/vc_redist.x64.exe"},"here"),",\nand install it by double-clicking on it. Once the Visual C++ redistributable runtime is installed,\ncheck that the Scala CLI runs fine by running its ",(0,o.kt)("inlineCode",{parentName:"p"},"about")," command:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli about\n")),(0,o.kt)("p",null,"Note that the commands above don't put the ",(0,o.kt)("inlineCode",{parentName:"p"},"scala-cli")," command in the ",(0,o.kt)("inlineCode",{parentName:"p"},"PATH"),". For that, you can create a directory, move the\nlauncher there, and add the directory to the ",(0,o.kt)("inlineCode",{parentName:"p"},"PATH")," with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},'md "%USERPROFILE%/scala-cli"\nscala-cli add-path "%USERPROFILE%/scala-cli"\nmove scala-cli.exe "%USERPROFILE%/scala-cli"\n'))),(0,o.kt)(m.Z,{value:"installer",mdxType:"TabItem"},(0,o.kt)("p",null,"Download MSI installer with Scala CLI for Windows"),(0,o.kt)(b,{desc:"Scala CLI for Windows",href:"https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-pc-win32.msi",width:"190px",mdxType:"DownloadButton"})),(0,o.kt)(m.Z,{value:"sdkman",mdxType:"TabItem"},(0,o.kt)("p",null,"Scala CLI can be installed via ",(0,o.kt)("a",{parentName:"p",href:"https://sdkman.io"},"SDKMAN")," with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"sdk install scalacli\n"))),(0,o.kt)(m.Z,{value:"choco",mdxType:"TabItem"},(0,o.kt)("p",null,"To install Scala CLI via ",(0,o.kt)("a",{parentName:"p",href:"https://chocolatey.org"},"Chocolatey"),", run the following command from the command line or from PowerShell:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"choco install scala-cli\n")),(0,o.kt)("div",{className:"admonition admonition-note alert alert--secondary"},(0,o.kt)("div",{parentName:"div",className:"admonition-heading"},(0,o.kt)("h5",{parentName:"div"},(0,o.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,o.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"14",height:"16",viewBox:"0 0 14 16"},(0,o.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M6.3 5.69a.942.942 0 0 1-.28-.7c0-.28.09-.52.28-.7.19-.18.42-.28.7-.28.28 0 .52.09.7.28.18.19.28.42.28.7 0 .28-.09.52-.28.7a1 1 0 0 1-.7.3c-.28 0-.52-.11-.7-.3zM8 7.99c-.02-.25-.11-.48-.31-.69-.2-.19-.42-.3-.69-.31H6c-.27.02-.48.13-.69.31-.2.2-.3.44-.31.69h1v3c.02.27.11.5.31.69.2.2.42.31.69.31h1c.27 0 .48-.11.69-.31.2-.19.3-.42.31-.69H8V7.98v.01zM7 2.3c-3.14 0-5.7 2.54-5.7 5.68 0 3.14 2.56 5.7 5.7 5.7s5.7-2.55 5.7-5.7c0-3.15-2.56-5.69-5.7-5.69v.01zM7 .98c3.86 0 7 3.14 7 7s-3.14 7-7 7-7-3.12-7-7 3.14-7 7-7z"}))),"note")),(0,o.kt)("div",{parentName:"div",className:"admonition-content"},(0,o.kt)("p",{parentName:"div"},"Third-party Chocolatey packages may not provide the latest version.")))))),(0,o.kt)(m.Z,{value:"mac",mdxType:"TabItem"},(0,o.kt)(p.Z,{groupId:"mac",defaultValue:"brew",values:[{label:"Manual",value:"manual"},{label:"Installer",value:"installer"},{label:"Brew",value:"brew"},{label:"Nix",value:"nix"},{label:"SDKMAN",value:"sdkman"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"manual",mdxType:"TabItem"},(0,o.kt)("p",null,"Download the launcher from GitHub release assets with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"curl -fL https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-apple-darwin.gz | gzip -d > scala-cli\nchmod +x scala-cli\nmv scala-cli /usr/local/bin/scala-cli\n")),(0,o.kt)("p",null,"Check that it runs fine by running its ",(0,o.kt)("inlineCode",{parentName:"p"},"about")," command:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli about\n"))),(0,o.kt)(m.Z,{value:"installer",mdxType:"TabItem"},(0,o.kt)("p",null,"Download PKG installer with Scala CLI for MacOS"),(0,o.kt)(b,{desc:"Scala CLI for MacOS",href:"https://github.com/Virtuslab/scala-cli/releases/latest/download/scala-cli-x86_64-apple-darwin.pkg",mdxType:"DownloadButton"}),(0,o.kt)("br",null),(0,o.kt)("br",null),(0,o.kt)("p",null,"Once downloaded, right-click on ",(0,o.kt)("inlineCode",{parentName:"p"},"scala-cli-x86_64-apple-darwin.pkg"),' from Finder, and choose "Open".')),(0,o.kt)(m.Z,{value:"brew",mdxType:"TabItem"},(0,o.kt)("p",null,"Scala CLI can be installed via ",(0,o.kt)("a",{parentName:"p",href:"https://brew.sh"},"homebrew")," with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"brew install Virtuslab/scala-cli/scala-cli\n"))),(0,o.kt)(m.Z,{value:"nix",mdxType:"TabItem"},(0,o.kt)("div",{className:"admonition admonition-info alert alert--info"},(0,o.kt)("div",{parentName:"div",className:"admonition-heading"},(0,o.kt)("h5",{parentName:"div"},(0,o.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,o.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"14",height:"16",viewBox:"0 0 14 16"},(0,o.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M7 2.3c3.14 0 5.7 2.56 5.7 5.7s-2.56 5.7-5.7 5.7A5.71 5.71 0 0 1 1.3 8c0-3.14 2.56-5.7 5.7-5.7zM7 1C3.14 1 0 4.14 0 8s3.14 7 7 7 7-3.14 7-7-3.14-7-7-7zm1 3H6v5h2V4zm0 6H6v2h2v-2z"}))),"info")),(0,o.kt)("div",{parentName:"div",className:"admonition-content"},(0,o.kt)("p",{parentName:"div"},"This method is provided and supported by the community, not the core team of Scala CLI"))),(0,o.kt)("p",null,"Scala CLI can be installed with ",(0,o.kt)("a",{parentName:"p",href:"https://nixos.org"},"Nix")," with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"nix-env -iA scala-cli\n"))),(0,o.kt)(m.Z,{value:"sdkman",mdxType:"TabItem"},(0,o.kt)("p",null,"Scala CLI can be installed via ",(0,o.kt)("a",{parentName:"p",href:"https://sdkman.io"},"SDKMAN")," with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"sdk install scalacli\n")))))))),(0,o.kt)(d.Z,{title:"Standalone launcher",mdxType:"SectionAbout"},(0,o.kt)("div",{className:"margin--lg"}),(0,o.kt)(p.Z,{defaultValue:"windows"==(0,k.l)()?"windows":"macOS/Linux",groupId:"specific-standalone-launcher",values:[{label:"macOS/Linux",value:"macOS/Linux"},{label:"Windows",value:"windows"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"macOS/Linux",mdxType:"TabItem"}),(0,o.kt)(m.Z,{value:"windows",mdxType:"TabItem"}))),(0,o.kt)("div",{className:"row"},(0,o.kt)("div",{className:"col col--9 col--offset-1 padding--lg advanced_install_methods"},(0,o.kt)("p",null,"Script to automatically download and cache standalone ",(0,o.kt)("inlineCode",{parentName:"p"},"scala-cli")," launcher."),(0,o.kt)(p.Z,{groupId:"specific-standalone-launcher",defaultValue:"macOS/Linux",values:[{label:"macOS/Linux",value:"macOS/Linux"},{label:"Windows",value:"windows"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"macOS/Linux",mdxType:"TabItem"},(0,o.kt)(b,{desc:"Scala CLI launcher for macOS/Linux",href:"https://github.com/VirtusLab/scala-cli/blob/main/scala-cli.sh",width:"250px",mdxType:"DownloadButton"})),(0,o.kt)(m.Z,{value:"windows",mdxType:"TabItem"},(0,o.kt)(b,{desc:"Scala CLI launcher for Windows",href:"https://github.com/VirtusLab/scala-cli/blob/main/scala-cli.bat",width:"250px",mdxType:"DownloadButton"}))))),(0,o.kt)(d.Z,{title:"Shell completions",mdxType:"SectionAbout"},(0,o.kt)("div",{className:"margin--lg"}),(0,o.kt)(p.Z,{defaultValue:"mac"==(0,k.l)()?"zsh":"bash",groupId:"shell-specific",values:[{label:"Bash",value:"bash"},{label:"zsh",value:"zsh"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"bash",mdxType:"TabItem"}),(0,o.kt)(m.Z,{value:"zsh",mdxType:"TabItem"}))),(0,o.kt)("div",{className:"row"},(0,o.kt)("div",{className:"col col--9 col--offset-1 padding--lg advanced_install_methods"},(0,o.kt)("p",null,"Try the completions with"),(0,o.kt)(p.Z,{groupId:"shell-specific",defaultValue:"bash",values:[{label:"Bash",value:"bash"},{label:"zsh",value:"zsh"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"bash",mdxType:"TabItem"},(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre"},'eval "$(scala-cli install completions --env --shell bash)"\nscala-cli --<TAB>\n'))),(0,o.kt)(m.Z,{value:"zsh",mdxType:"TabItem"},(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre"},'eval "$(scala-cli install completions --env --shell zsh)"\nscala-cli --<TAB>\n')))),(0,o.kt)("p",null,"Install them on your system with"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli install completions\n")),(0,o.kt)("p",null,"If any of the ",(0,o.kt)("inlineCode",{parentName:"p"},"scala-cli install completions")," command complained that your shell cannot be determined, specify it\nwith ",(0,o.kt)("inlineCode",{parentName:"p"},"--shell")),(0,o.kt)(p.Z,{groupId:"shell-specific",values:[{label:"Bash",value:"bash"},{label:"zsh",value:"zsh"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"bash",mdxType:"TabItem"},(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli install completions --shell bash\n"))),(0,o.kt)(m.Z,{value:"zsh",mdxType:"TabItem"},(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli install completions --shell zsh\n")))))),(0,o.kt)("div",{id:"scala-js"}),(0,o.kt)(d.Z,{title:"Scala.js",mdxType:"SectionAbout"}),(0,o.kt)("div",{className:"row"},(0,o.kt)("div",{className:"col col--9 col--offset-1 padding--lg advanced_install_methods"},(0,o.kt)("p",null,"To run Scala.js applications ",(0,o.kt)("a",{parentName:"p",href:"https://nodejs.org/"},"Node.js")," needs to be installed. Scala CLI at this moment does not manage Node.js however it may change in the future."))),(0,o.kt)("div",{id:"scala-native"}),(0,o.kt)(d.Z,{title:"Scala Native",mdxType:"SectionAbout"}),(0,o.kt)("div",{className:"row"},(0,o.kt)("div",{className:"col col--9 col--offset-1 padding--lg advanced_install_methods"},(0,o.kt)("p",null,(0,o.kt)("a",{parentName:"p",href:"https://llvm.org/docs/GettingStarted.html#requirements"},"Clang")," is required to compile and run Scala Native applications. Using some functionalities known from JDK (like using ",(0,o.kt)("inlineCode",{parentName:"p"},"java.util.zip")," package) require additional packages to be installed."),(0,o.kt)("p",null,"Scala Native page contains detailed ",(0,o.kt)("a",{parentName:"p",href:"https://scala-native.readthedocs.io/en/latest/user/setup.html#installing-clang-and-runtime-dependencies"},"installation guide"),"."))),(0,o.kt)(d.Z,{title:"Uninstall scala CLI",mdxType:"SectionAbout"},(0,o.kt)("div",{className:"margin--lg"}),(0,o.kt)(p.Z,{groupId:"uninstall-specific",defaultValue:(0,k.l)(),values:[{label:"Windows",value:"windows"},{label:"MacOs",value:"mac"},{label:"Linux",value:"linux"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"windows",mdxType:"TabItem"}),(0,o.kt)(m.Z,{value:"mac",mdxType:"TabItem"}),(0,o.kt)(m.Z,{value:"linux",mdxType:"TabItem"}))),(0,o.kt)("div",{className:"row"},(0,o.kt)("div",{className:"col col--9 col--offset-1 padding--lg advanced_install_methods"},(0,o.kt)("p",null,"Pick the uninstallation method:"),(0,o.kt)(p.Z,{groupId:"uninstall-specific",defaultValue:(0,k.l)(),values:[{label:"Windows",value:"windows"},{label:"MacOs",value:"mac"},{label:"Linux",value:"linux"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"linux",mdxType:"TabItem"},(0,o.kt)(p.Z,{groupId:"linux",defaultValue:"installation-script-u",values:[{label:"Installation script",value:"installation-script-u"},{label:"Manual",value:"manual-u"},{label:"Apt",value:"apt-u"},{label:"Deb",value:"deb-u"},{label:"Yum",value:"yum-u"},{label:"Rpm",value:"rpm-u"},{label:"Alpine",value:"alpine-u"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"installation-script-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed via the installation script, you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli uninstall\n"))),(0,o.kt)(m.Z,{value:"manual-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed manually you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"rm /usr/local/bin/scala-cli\n"))),(0,o.kt)(m.Z,{value:"apt-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed via ",(0,o.kt)("a",{parentName:"p",href:"https://wiki.debian.org/Apt"},"apt")," you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"sudo apt purge scala-cli\n"))),(0,o.kt)(m.Z,{value:"deb-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed via ",(0,o.kt)("a",{parentName:"p",href:"https://en.wikipedia.org/wiki/Dpkg"},"dpkg")," you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"sudo dpkg --remove scala-cli\n"))),(0,o.kt)(m.Z,{value:"yum-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed via ",(0,o.kt)("a",{parentName:"p",href:"https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/5/html/deployment_guide/c1-yum"},"yum")," you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"yum remove scala-cli\n"))),(0,o.kt)(m.Z,{value:"rpm-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed via ",(0,o.kt)("a",{parentName:"p",href:"https://rpm.org/"},"rpm")," you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"rpm -e scala-cli\n"))),(0,o.kt)(m.Z,{value:"alpine-u",mdxType:"TabItem"},(0,o.kt)("p",null,"For Alpine Linux you can uninstall scala-cli with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"rm /usr/bin/scala-cli\n"))))),(0,o.kt)(m.Z,{value:"windows",mdxType:"TabItem"},(0,o.kt)(p.Z,{groupId:"windows",defaultValue:"manual-u",values:[{label:"Manual",value:"manual-u"},{label:"Installer",value:"installer-u"},{label:"Chocolatey",value:"choco"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"manual-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed manually you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},'rmdir "%USERPROFILE%/scala-cli"\n'))),(0,o.kt)(m.Z,{value:"installer-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed via the installer, you can uninstall it in the Control Panel.")),(0,o.kt)(m.Z,{value:"choco",mdxType:"TabItem"},(0,o.kt)("p",null,"To uninstall Scala CLI via ",(0,o.kt)("a",{parentName:"p",href:"https://chocolatey.org"},"Chocolatey"),", run the following command from the command line or from PowerShell:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"choco uninstall scala-cli\n"))))),(0,o.kt)(m.Z,{value:"mac",mdxType:"TabItem"},(0,o.kt)(p.Z,{groupId:"mac",defaultValue:"installation-script-u",values:[{label:"Installation script",value:"installation-script-u"},{label:"Manual",value:"manual-u"},{label:"Brew",value:"brew-u"}],mdxType:"Tabs"},(0,o.kt)(m.Z,{value:"installation-script-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed via the installation script, you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"scala-cli uninstall\n"))),(0,o.kt)(m.Z,{value:"manual-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed manually you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"rm /usr/local/bin/scala-cli\n"))),(0,o.kt)(m.Z,{value:"brew-u",mdxType:"TabItem"},(0,o.kt)("p",null,"If Scala CLI was installed via ",(0,o.kt)("a",{parentName:"p",href:"https://brew.sh"},"homebrew")," you can uninstall it with:"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-bash"},"brew uninstall scala-cli\n")))))))))}f.isMDXComponent=!0;var w=function(a){return t.createElement(n.Z,{title:"Install Scala CLI",description:"How to install Scala CLI"},t.createElement("div",{className:"container content"},t.createElement(i.Z,{className:"section-install-cli"},t.createElement("div",{className:"row"},t.createElement(s.Z,{title:"Quick start",colsize:"4",promptsign:!0}),t.createElement("div",{className:"col col--8"},t.createElement(c.Z,null)))),t.createElement(i.Z,{className:"section-about advanced-install"},t.createElement(f,null))))}}}]);