package com.prieb_gifaldi.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

public class flappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	ShapeRenderer shapeRenderer;

	Texture[] birds;
	Texture topTube;
	Texture bottomTube;
	int flapState = 0;
	float birdY = 0;
	int birdX = 0;
	int birdWidth;
	int birdHeight;
	double velocity = 0;
	int gameState = 0; // Start State
	double gravity = 1.8;
	double gap = 400;
	double maxTubeOffSet = 0;
	Random randomGenerator;
	double [] tubeOffSet = new double[4];
	double tubeVelocity = 4;
	double [] tubeX = new double[4];
	int numberOfTubes = 4;
	double distanceBetweenTubes;
	Circle birdCircle= new Circle();



	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		bottomTube = new Texture ("bottomtube.png");
		birdY = Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;
		birdX = Gdx.graphics.getWidth()/2 - birds[0].getWidth()/2;
		birdWidth = birds[flapState].getWidth();
		birdHeight = birds[flapState].getHeight();
		maxTubeOffSet = Gdx.graphics.getHeight()/2 - gap/2 - 100;
		randomGenerator = new Random();
		distanceBetweenTubes = 3*Gdx.graphics.getWidth()/4;
		shapeRenderer = new ShapeRenderer();

		for(int i =0; i < numberOfTubes; i++)
		{
			tubeOffSet[i] = (randomGenerator.nextDouble() - 0.5)*(2*maxTubeOffSet);
			tubeX[i] = Gdx.graphics.getWidth()/2 - topTube.getWidth()/2 + distanceBetweenTubes*i;

		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (gameState != 0) {
			if (Gdx.input.justTouched()) {

				velocity = -27;
			}
			for(int i =0; i < numberOfTubes; i++)
			{
				if(tubeX[i] < -topTube.getWidth()) {
					tubeX[i] += numberOfTubes*distanceBetweenTubes;
					tubeOffSet[i] = (randomGenerator.nextDouble() - 0.5)*(2*maxTubeOffSet);

				}
				tubeX[i] -= tubeVelocity;
				batch.draw(topTube, (int)tubeX[i], (int)(Gdx.graphics.getHeight()/2  + gap/2 + tubeOffSet[i])) ;
				batch.draw(bottomTube, (int)tubeX[i], (int)(Gdx.graphics.getHeight()/2 - gap/2 - bottomTube.getHeight() + tubeOffSet[i]));

			}

			if(birdY > 0 || velocity < 0) {
				velocity += gravity;
				birdY -= velocity;
			}
		}
		else
		{

			if (Gdx.input.justTouched()) {

				Gdx.app.log("Touched", "Yep!");

				gameState = 1;
			}

		}
		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}
		batch.draw(birds[flapState], birdX, (int) birdY, birdWidth, birdHeight);
		batch.end();

		birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth()/2);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
		shapeRenderer.end();




	}
}
