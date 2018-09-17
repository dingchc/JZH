package com.jzh.parents.widget;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jzh.parents.R;
import com.jzh.parents.adapter.BannerAdapter;
import com.jzh.parents.app.JZHApplication;
import com.jzh.parents.datamodel.data.BannerData;
import com.jzh.parents.listener.IActionUpListener;
import com.jzh.parents.utils.AppLogger;
import com.jzh.parents.utils.Util;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图
 *
 * @author Ding
 */
public class CircleBannerPage extends RelativeLayout implements
        IActionUpListener {

    private ViewPager bannerViewPager;
    private LinearLayout indicatorLayout;
    private List<BannerData> adList = null;

    private int pictureCntInPager = 0;
    private Context context;
    private ImageView[] dotImageViews;
    private boolean isContinue = true;
    private int currentIndex = 0;
    private int currentDotIndex = -1;

    private long lastActionTimeMillis = 0;

    private Paint paint;
    private int screenWidth = 0;
    private int canvasHeight = 0;
    private int dividerDimen = 0;

    private BannerClickListener bannerClickListener;

    public CircleBannerPage(Context context) {
        this(context, null);
    }

    public CircleBannerPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBannerPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        dividerDimen = getResources().getDimensionPixelSize(R.dimen.divider_line_dimen);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.gray));
        paint.setStrokeWidth(dividerDimen);

        screenWidth = getResources().getDisplayMetrics().widthPixels;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.layout_banner, this);
        bannerViewPager = v.findViewById(R.id.vp_banner);
        indicatorLayout = v.findViewById(R.id.ll_indicator);

        if (isInEditMode()) {
            return;
        }

    }

    public void setAdList(List<BannerData> adList) {
        this.adList = adList;
    }

    public void addChildrenViews() {

        int count = this.adList.size();

        if (count <= 0) {
            return;
        }

        indicatorLayout.removeAllViews();
        bannerViewPager.removeAllViews();

        List<View> advPics = new ArrayList<View>();
        for (int i = 0; i < count; i++) {

            final BannerData info = adList.get(i);

            // 预留开始的
            if (i == 0 && count > 1) {

                BannerData preAd = adList.get(count - 1);
                ImageView preImg = createBannerImageView(preAd, count - 1);
                advPics.add(preImg);
            }

            ImageView img = createBannerImageView(info, i);

            advPics.add(img);

            // 预留结尾的
            if (i == count - 1 && count > 1) {
                BannerData afterAd = adList.get(0);
                ImageView afterImg = createBannerImageView(afterAd, 0);
                advPics.add(afterImg);
            }
        }

        dotImageViews = new ImageView[count];
        pictureCntInPager = advPics.size();

        ImageView dotImageView;

        int dotImageWidth = Util.Companion.dp2px(context, 6);

        for (int i = 0; i < count; i++) {
            dotImageView = new ImageView(context);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dotImageWidth, dotImageWidth);
            lp.setMargins(5, 0, 5, 0);

            dotImageView.setLayoutParams(lp);
            dotImageView.setPadding(5, 5, 5, 5);

            dotImageViews[i] = dotImageView;
            if (i == 0) {
                dotImageViews[i]
                        .setBackgroundResource(R.mipmap.page_indicator_focused);
                currentDotIndex = i;
            } else {
                dotImageViews[i]
                        .setBackgroundResource(R.mipmap.page_indicator);
            }

            indicatorLayout.addView(dotImageViews[i]);
        }
        bannerViewPager.setAdapter(new BannerAdapter(advPics));

        bannerViewPager.setOffscreenPageLimit(count - 1 > 0 ? count - 1 : count);
        bannerViewPager.setCurrentItem(1, false);

        bannerViewPager.addOnPageChangeListener(new GuidePageChangeListener());

    }

    private ImageView createBannerImageView(final BannerData info, final int position) {

        RoundedImageView img = new RoundedImageView(context);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setCornerRadius(Util.Companion.dp2px(context, 8.0f));
        img.setBorderWidth(0.0f);
        img.mutateBackground(false);


        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.bg_home_mask);
        Glide.with(JZHApplication.Companion.getInstance()).load(info.getImgUrl()).apply(options).into(img);

        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bannerClickListener != null) {
                    bannerClickListener.onBannerClick(position);
                    return;
                }

                String pageurl = info.getLinkUrl();

                AppLogger.i("click");

            }
        });

        return img;
    }

    private final Handler viewHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {

            long cimeMillis = System.currentTimeMillis() - lastActionTimeMillis;

            if (adList == null || adList.size() <= 0) {

                return;
            }

            if (isContinue && cimeMillis >= 5000) {

                int nextPage = getNextPage();

                if (nextPage >= 0) {
                    bannerViewPager.setCurrentItem(getNextPage(), true);
                }
            }
            viewHandler.sendEmptyMessageDelayed(0, 5000);

        }

    };

    /**
     * 获取下一页索引
     *
     * @return 索引
     */
    private int getNextPage() {

        int nextPage = 0;

        if (adList.size() <= 0) {
            return -1;
        }

        currentIndex = bannerViewPager.getCurrentItem();
        nextPage = (currentIndex + 1) % pictureCntInPager;

        return nextPage;
    }

    private final class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {

            if (pictureCntInPager <= 1 || state != ViewPager.SCROLL_STATE_IDLE) {
                return;
            }

            if (bannerViewPager.getCurrentItem() == 0) {
                bannerViewPager.setCurrentItem(pictureCntInPager - 2, false);
            } else if (bannerViewPager.getCurrentItem() == pictureCntInPager - 1) {
                bannerViewPager.setCurrentItem(1, false);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int index) {

            if (pictureCntInPager <= 1) {
                return;
            }

            if (index == 0) {
                setDotIndex(pictureCntInPager - 2 - 1);

            } else if (index == pictureCntInPager - 1) {
                setDotIndex(0);
            } else {
                setDotIndex(index - 1);
            }

        }

    }

    private void setDotIndex(int index) {

        if (index == currentDotIndex) {

            return;
        }

        if (currentDotIndex != -1) {
            dotImageViews[currentDotIndex]
                    .setBackgroundResource(R.mipmap.page_indicator);
        }

        currentDotIndex = index;

        dotImageViews[currentDotIndex]
                .setBackgroundResource(R.mipmap.page_indicator_focused);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        viewHandler.removeMessages(0);
    }

    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();

        viewHandler.removeMessages(0);
        viewHandler.sendEmptyMessageDelayed(0, 5000);
    }

    @Override
    public void actionEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isContinue = false;
                break;
            case MotionEvent.ACTION_MOVE:
                isContinue = false;
                break;
            case MotionEvent.ACTION_UP:
                lastActionTimeMillis = System.currentTimeMillis();
                isContinue = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                lastActionTimeMillis = System.currentTimeMillis();
                isContinue = true;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                lastActionTimeMillis = System.currentTimeMillis();
                isContinue = true;
                break;
            default:
                isContinue = true;
                break;
        }

    }

    public void setBannerClickListener(BannerClickListener listener) {
        this.bannerClickListener = listener;
    }

    /**
     * Banner图点击监听
     */
    public interface BannerClickListener {
        /**
         * 点击了Banner图
         *
         * @param position 位置
         */
        void onBannerClick(int position);
    }

}