
var cssTemp;
//estilo con proposal a la izquierda
var cssNormal;

$(document).on("ready",inicio);
function inicio()
{
	$("#flotante h2").on('click',mostrarProposal);	
	//cuando le den click a un article del proposal
	$("#intereses article").on("click",checkArticle);
	$("#send").on("click",validarFormulario);
	cssTemp = 
	{
		"height" : $("#flotante").height()
	};
	cssNormal=
	{
		"width":$("#flotante").css("width"),
		"left":$("#flotante").css("left"),
		"height":$("#flotante").css("height")
	};
	
}
var band=true;
function stopScroll(e)
{
	e.preventDefault();
}
function mostrarProposal(event)
{
	/*---------------------------------------------------------------------*/
	/*---------------JavaScrip para section flotante proposal--------------*/
		var css;
		var background;
		//guardar por defecto
		if(band)
		{
			

			
			//Json con los cambios en css
			if($(window).width()<=767)
			{
				css=
				{
					"height":"100%",
					"overflow":"auto"
				};
				//Mostrar la flecha de retorno
				background=
				{
					"background": "url(../img/back.png) no-repeat 5%",
					"background-size": "32px",
					"background-position": "5% 0px"
				};
				$("#flotante h2").css(background);
				//Mostrar el contenido del formulario de proposal
				$("#proposal").css("display","block");
			}
			else
			{
				//Desctivar el scroll por defecto en tablet
				
				$("#header, #content, #footer, #copyright").bind('touchmove', stopScroll);
				css=
				{
					"height":"80%",
					"left":"30%",
					"overflow":"auto",
					"width":"60%"
				};
				$("#proposal").css("display","block");
				//Girar el texto get proposal 
				$("#flotante h2").css("-webkit-transform","none");
				$("#flotante h2").css("-moz-transform","none");
				$("#flotante h2").css("-o-transform","none");
				$("#flotante h2").css("-ms-transform","none");
				$("#flotante h2").css("transform","none");
				//Ajustar texto h2 para que quede centrado horizontalmente
				$("#flotante h2").css("width","100%");
				$("#flotante h2").css("height","2em");
			}
			band=!band;
			//bloqueaar scroll browser
			//$("body").css("overflow","hidden");
		}
		else
		{

			//Activar el scroll por defecto mobiles
			$("#header, #content, #footer, #copyright").unbind('touchmove');
			//activar scroll browser
			$("body").css("overflow","auto");

			band=1;
			//Ocultar el formulario de proposal
			$("#proposal").css("display","none");
			
			if($(window).width()<=767)
			{
				
				//Ocultar la flecha de regresar de proposal
				$("#flotante h2").css("background","none");	
				css=cssTemp;
			}
			else
			{
				//Regresar al estado normal por defecto
				css=cssNormal;
				//Rotar para que quede vertical el texto
				$("#flotante h2").css("-webkit-transform","rotate(270deg)");
				$("#flotante h2").css("-moz-transform","rotate(270deg)");
				$("#flotante h2").css("-o-transform","rotate(270deg)");
				$("#flotante h2").css("transform","rotate(270deg)");
				//Ajustar texto h2 para que quede por defecto
				$("#flotante h2").css("width","200px");
				$("#flotante h2").css("height","200px");
				//Desactivar scroll proposal
				$("#flotante").css("overflow","hidden");
			}
		}
		$("#flotante").css(css);

		var salida="Aplicar: ";
		for (property in css)
		{
			salida+= property + ":"+css[property]+";";
		}
		console.log(salida);
		
		console.log("Aplicado");

		/*---------------------------------------------------------------------*/
		/*---------------Secciones que se ocultan en mobiles-------------------*/

		if($(window).width()<=767)
		{
			if(band==0)
			{
				//ocultar demas contenido de la pagina
				$("#header, #content, #footer, #copyright").css("display","none");
				//$("#header").css("display","none");
			}
			else
			{
				//mostrar resto de la pagina
				$("#header, #content, #footer, #copyright").css("display","block");
				//$("#header").css("display","block");
			}
		}

		/*---------------------------------------------------------------------*/
		/*---------------FIN section flotante proposal--------------*/

}
function checkArticle(datos)
{
	console.log(datos);
	console.log(datos.delegateTarget.children[1].children[0].checked);
	var estado=datos.delegateTarget.children[1].children[0].checked;
	datos.delegateTarget.children[1].children[0].checked=!estado;
	console.log(datos.delegateTarget.children[1].children[0].checked);
}

function validarFormulario()
{
	
	var min=$("#min").attr("value");
	var max=$("#max").attr("value");
	console.log("min:"+min+",max:"+max);
	if(min>max)
	{
		console.log("cambiando");
		$("#min").attr("value",max);
		$("#max").attr("value",min);
	}
	else
	{
		console.log("no cambiar");
	}
}