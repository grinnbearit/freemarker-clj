${string("apple")}${number(5)}${boolean(false)}${sequence([1,"2",3])}${hash({"a":1,"b":2})}
${count([1, "2", 3])}
${count({"a": 1, "b": 2})}
${return_string()}
${return_number()}
<#if return_boolean()>sane<#else>crazy</#if>
${return_sequence()[1]}
${return_hash().b}
${return_date()?datetime}
