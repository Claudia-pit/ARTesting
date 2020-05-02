package com.example.artesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {

    lateinit var andyRenderable : ModelRenderable
    lateinit var arFragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = supportFragmentManager.findFragmentById(R.id.activity_3D) as ArFragment

        ModelRenderable.builder()
            .setSource(this, R.raw.andy)
            .build()
            .thenAccept{ modelRenderable -> andyRenderable = modelRenderable }
            .exceptionally { throwable ->
                val toast = Toast.makeText(this, "Cannot load model", Toast.LENGTH_LONG)
                toast.show()
                null
            }

        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            val andy = TransformableNode(arFragment.transformationSystem)
            andy.setParent(anchorNode)
            andy.renderable = andyRenderable
            andy.select()
        }
    }
}
