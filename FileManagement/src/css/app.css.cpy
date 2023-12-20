/*
    Document   : override
    Created on : <date>
    Author     : <author>
    Description:
        This is where any of your application specific styles should be included
*/

.sendOffScreen {
  position: absolute;
  left: -10000px;
  top: auto;
  width: 1px;
  height: 1px;
  overflow: hidden;
}

.demo-oracle-icon {
  width:137px;
  height:18px
}

.demo-oracle-icon:before {
  content:url("images/oracle_logo.svg")
}

html:not([dir="rtl"]) .demo-oracle-icon {
  padding-right:4px
}

html[dir="rtl"] .demo-oracle-icon {
  padding-left:4px
}

.demo-oracle-icon:before {
  display:inline
}

.demo-appheader-avatar {
  width:24px;
  height:24px
}

.demo-appheader-avatar:before {
  content:url("images/avatar_24px.png")
}
@media (-webkit-min-device-pixel-ratio: 1.5), (min-resolution: 144dpi), (min-resolution: 1.5dppx) {
  .demo-appheader-avatar:before {
      content:url("images/avatar_24px_2x.png");
      -webkit-transform:translate(-25%, -25%) scale(0.5);
      transform:translate(-25%, -25%) scale(0.5)
  }
}