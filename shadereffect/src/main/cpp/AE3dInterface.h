#ifndef __EFFECT_AE3D_INTERFACE_H__
#define __EFFECT_AE3D_INTERFACE_H__
#include <memory>
#include <string>

#if defined(ANDROID)
#define EXPORT __attribute ((visibility("default")))
#elif defined(WIN32)
#define EXPORT
#else
#define EXPORT
#endif

namespace AYSDK {
namespace EFFECT {
//////////////////////////////////////////////////////////////////////////
class ConfigurableGame;
//////////////////////////////////////////////////////////////////////////
class EXPORT AE3d {
public:
	~AE3d( );

public:
	typedef std::shared_ptr<AE3d> SmartAE3d;
	// game type
	enum EType {
		PARALLAX_SLIDE = 0x0001,
		FRAME_DISPLAY,
	};
	// texture format
	enum Format
	{
		UNKNOWN = 0,
		RGB,
		RGB888 = RGB,
		RGB565,
		RGBA,
		RGBA8888 = RGBA,
		RGBA4444,
		RGBA5551,
		ALPHA,
		DEPTH,
	};

public:
	static SmartAE3d Create( EType type, int width, int height );

public:
	int set( const std::string& key, int value );
	int set( const std::string& key, float value );
	int set( const std::string& key, const std::string& value );
	int set( const std::string& key, unsigned texId, int width, int height, Format format );
	int set( const std::string& key, void* pValue );

public:
	void initialize( );
	void draw( );
	void finialize( );

protected:
	AE3d( );

private:
	typedef std::shared_ptr<ConfigurableGame> SmartGame;
	SmartGame _game;
};
//////////////////////////////////////////////////////////////////////////
}
}

#endif