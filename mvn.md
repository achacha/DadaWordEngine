Building and signing
===
mvn clean package verify


If signing gives error "gpg: signing failed: Inappropriate ioctl for device"
---
export GPG_TTY=$(tty)


