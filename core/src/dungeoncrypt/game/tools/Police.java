package dungeoncrypt.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static dungeoncrypt.game.data.Data.FONT_SIZE;

public class Police {

    private BitmapFont font;

    private static final Police instance = new Police(){};

    public static Police getInstance() {
        return instance;
    }

    private Police(){
        FreeTypeFontGenerator fGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fparams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fparams.size = FONT_SIZE;
        fparams.color = new Color(255f, 0f, 0f, 0.5f);
        fparams.borderColor = Color.BLACK;
        fparams.borderWidth = 3;
        this.font = fGen.generateFont(fparams);
        this.font.getData().setScale(1);
        this.font.setUseIntegerPositions(false);
        fGen.dispose();
    }

    public void draw(SpriteBatch batch,String message,float x, float y){
        font.draw(batch, message, x,y);
    }

}

