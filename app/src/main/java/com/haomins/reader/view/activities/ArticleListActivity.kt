package com.haomins.reader.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.haomins.reader.R
import com.haomins.reader.utils.QuickToast

class ArticleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.start_slide_in_left, R.anim.start_slide_out_left)
        setContentView(R.layout.activity_article_list)
        QuickToast.show(this, intent.getStringExtra("FEED_ID"))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.start_slide_in_right, R.anim.start_slide_out_right)
    }
}