<%@ page contentType="text/plain;charset=UTF-8" %>
<%@ taglib prefix="d" uri="/taglib/dada" %>
A rhyming poem about <d:noun article="a" save="subject"/>.
---
<d:noun form="plural" capMode="first" load="subject"/> are <d:adjective save="rhyme0"/>,
<d:noun form="plural" capMode="first"/> are <d:adjective save="rhyme1"/>.

<d:noun article="a" capMode="first"/> is really <d:adjective rhyme="rhyme0"/>
  and it can also be <d:adjective rhyme="rhyme1"/>.
