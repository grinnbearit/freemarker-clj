${data.string}
${data.number}
<#if data.boolean>sane<#else>crazy</#if>
${data.sequence[1]}
${data.hash.a}
${data.date?datetime}
${data.fn(2)}
${data.with_hyphens}
${data.nested[1].b}
${data.nested[2](1)}
