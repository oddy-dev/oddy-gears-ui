package com.oddy.gearsui.compose

import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.oddy.gearsui.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class GearsTextType(
    val textSize: TextUnit,
    val lineHeight: TextUnit,
    val fontWeight: FontWeight? = null
) {
    object Heading30 : GearsTextType(30.sp, 36.sp, FontWeight.Bold)
    object Heading24 : GearsTextType(24.sp, 32.sp, FontWeight.Bold)
    object Heading20 : GearsTextType(20.sp, 24.sp, FontWeight.Bold)
    object Heading18 : GearsTextType(18.sp, 22.sp, FontWeight.Bold)
    object Heading16 : GearsTextType(16.sp, 20.sp, FontWeight.Bold)
    object Heading14 : GearsTextType(14.sp, 18.sp, FontWeight.Bold)
    object Body16 : GearsTextType(16.sp, 20.sp)
    object Body14 : GearsTextType(14.sp, 18.sp)
    object Body12 : GearsTextType(12.sp, 14.sp)
}

@Composable
fun GearsText(
    text: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit? = null,
    textColor: Color = colorResource(id = R.color.black_900),
    type: GearsTextType = GearsTextType.Body14,
    textAlign: TextAlign? = null,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit? = null,
    fontWeight: FontWeight? = null,
    maxLines: Int = Int.MAX_VALUE,
    fontStyle: FontStyle = FontStyle.Normal,
    textStyle: TextStyle = TextStyle.Default,
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontSize = textSize ?: type.textSize,
        lineHeight = lineHeight ?: type.lineHeight,
        fontWeight = fontWeight ?: type.fontWeight,
        fontFamily = FontFamily(Font(resId = R.font.effra)),
        textAlign = textAlign,
        letterSpacing = letterSpacing,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        fontStyle = fontStyle,
        style = textStyle,
    )
}

@Composable
fun GearsText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textSize: TextUnit? = null,
    textColor: Color = colorResource(id = R.color.black_900),
    type: GearsTextType = GearsTextType.Body14,
    textAlign: TextAlign? = null,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit? = null,
    fontWeight: FontWeight? = null,
    maxLines: Int = Int.MAX_VALUE,
    fontStyle: FontStyle = FontStyle.Normal,
    textStyle: TextStyle = TextStyle.Default,
    inlineContent: Map<String, InlineTextContent> = mapOf()
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontSize = textSize ?: type.textSize,
        lineHeight = lineHeight ?: type.lineHeight,
        fontWeight = fontWeight ?: type.fontWeight,
        fontFamily = FontFamily(Font(resId = R.font.effra)),
        textAlign = textAlign,
        letterSpacing = letterSpacing,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        fontStyle = fontStyle,
        inlineContent = inlineContent,
        style = textStyle,
    )
}

@Composable
fun GearsClickableText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var clickableJob by remember { mutableStateOf<Job?>(null) }

    ClickableText(
        text = text,
        modifier = modifier,
        style = style,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        onClick = { offset ->
            if (clickableJob == null) {
                clickableJob = coroutineScope.launch {
                    onClick(offset)

                    delay(500)
                    clickableJob = null
                }
            }
        }
    )
}