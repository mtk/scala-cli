"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[36],{3905:function(e,t,n){n.d(t,{Zo:function(){return l},kt:function(){return d}});var o=n(7294);function r(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function i(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,o)}return n}function a(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?i(Object(n),!0).forEach((function(t){r(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):i(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function c(e,t){if(null==e)return{};var n,o,r=function(e,t){if(null==e)return{};var n,o,r={},i=Object.keys(e);for(o=0;o<i.length;o++)n=i[o],t.indexOf(n)>=0||(r[n]=e[n]);return r}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(o=0;o<i.length;o++)n=i[o],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(r[n]=e[n])}return r}var s=o.createContext({}),u=function(e){var t=o.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):a(a({},t),e)),n},l=function(e){var t=u(e.components);return o.createElement(s.Provider,{value:t},e.children)},p={inlineCode:"code",wrapper:function(e){var t=e.children;return o.createElement(o.Fragment,{},t)}},f=o.forwardRef((function(e,t){var n=e.components,r=e.mdxType,i=e.originalType,s=e.parentName,l=c(e,["components","mdxType","originalType","parentName"]),f=u(n),d=r,b=f["".concat(s,".").concat(d)]||f[d]||p[d]||i;return n?o.createElement(b,a(a({ref:t},l),{},{components:n})):o.createElement(b,a({ref:t},l))}));function d(e,t){var n=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var i=n.length,a=new Array(i);a[0]=f;var c={};for(var s in t)hasOwnProperty.call(t,s)&&(c[s]=t[s]);c.originalType=e,c.mdxType="string"==typeof e?e:r,a[1]=c;for(var u=2;u<i;u++)a[u]=n[u];return o.createElement.apply(null,a)}return o.createElement.apply(null,n)}f.displayName="MDXCreateElement"},3963:function(e,t,n){n.r(t),n.d(t,{frontMatter:function(){return c},contentTitle:function(){return s},metadata:function(){return u},toc:function(){return l},default:function(){return f}});var o=n(3117),r=n(102),i=(n(7294),n(3905)),a=["components"],c={title:"Introduction",sidebar_position:1},s="Cookbook",u={unversionedId:"cookbooks/intro",id:"cookbooks/intro",title:"Introduction",description:"This section of the documentation contains a set of recipes that show how to use scala-cli in certain situations.",source:"@site/docs/cookbooks/intro.md",sourceDirName:"cookbooks",slug:"/cookbooks/intro",permalink:"/docs/cookbooks/intro",editUrl:"https://github.com/Virtuslab/scala-cli/edit/main/website/docs/cookbooks/intro.md",tags:[],version:"current",sidebarPosition:1,frontMatter:{title:"Introduction",sidebar_position:1},sidebar:"tutorialSidebar",previous:{title:"Internals",permalink:"/docs/guides/internals"},next:{title:"Packaging Scala applications as executable files",permalink:"/docs/cookbooks/scala-package"}},l=[],p={toc:l};function f(e){var t=e.components,n=(0,r.Z)(e,a);return(0,i.kt)("wrapper",(0,o.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,i.kt)("h1",{id:"cookbook"},"Cookbook"),(0,i.kt)("p",null,"This section of the documentation contains a set of recipes that show how to use ",(0,i.kt)("inlineCode",{parentName:"p"},"scala-cli")," in certain situations.\nThe recipes are intended to provide a solution to the task at hand, but also without going into great detail."),(0,i.kt)("p",null,"For more in-depth analysis, please check out our ",(0,i.kt)("a",{parentName:"p",href:"/docs/guides/intro"},"Guides")))}f.isMDXComponent=!0}}]);