precision highp float;

///////////////////////////////////////////////////////////
// Uniforms
uniform sampler2D 	u_diffuseTexture;
//uniform float 		u_aspectio;
//uniform float 		u_radius;

///////////////////////////////////////////////////////////
// Varyings
varying vec2 v_texCoord;
varying vec3 v_normal;

void main()
{
	//float width = 640.0;
	//float height = 360.0;
	//vec2 winCoord = vec2( gl_FragCoord.x/width, gl_FragCoord.y/height );
	//float r = distance( vec2(0.5*u_aspectio, 0.5), vec2(winCoord.x*u_aspectio, winCoord.y) );
	//if(r>u_radius)
	//	discard;
    //gl_FragColor = texture2D(u_diffuseTexture, v_texCoord);
	
	//float width = 640.0;
	//float height = 360.0;
	//float nx = gl_FragCoord.x / width;
	//float ny = gl_FragCoord.y / height;
	//float r = distance( vec2(0.5*u_aspectio, 0.5), vec2(nx*u_aspectio, ny) );
	//if(r>u_radius)
	//	discard;
	//gl_FragColor = texture2D( u_diffuseTexture, vec2(nx,ny) );
	
	gl_FragColor = texture2D( u_diffuseTexture, v_texCoord );
	gl_FragColor.a = 1.0;
}
