package dungeoncrypt.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import static dungeoncrypt.game.data.Data.FONT_SIZE;

public class Police {

    private final BitmapFont scoreFont;
    private final BitmapFont hpFont;

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
        this.scoreFont = fGen.generateFont(fparams);
        this.scoreFont.getData().setScale(1);
        this.scoreFont.setUseIntegerPositions(false);
        fGen.dispose();

        FreeTypeFontGenerator fGen2 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fparams2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fparams2.size = FONT_SIZE/2;
        fparams2.color = new Color(255f, 255f, 255f, 0.5f);
        fparams2.borderColor = Color.BLACK;
        fparams2.borderWidth = 1;
        this.hpFont = fGen2.generateFont(fparams2);
        this.hpFont.getData().setScale(1);
        this.hpFont.setUseIntegerPositions(false);
        fGen2.dispose();
    }

    public void drawScore(SpriteBatch batch,String message,float x, float y){
        scoreFont.draw(batch, message, x,y);
    }

    public void drawHP(SpriteBatch batch,String message,float x, float y){
        hpFont.draw(batch, message, x,y);
    }

}

