package com.example.ute.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.models.Review;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;
    private SessionManager sessionManager;
    private ApiService apiService;
    private OnReviewLikeListener likeListener;

    public interface OnReviewLikeListener {
        void onLikeChanged(Review review);
    }

    public ReviewAdapter(List<Review> reviews, SessionManager sessionManager, OnReviewLikeListener listener) {
        this.reviews = reviews;
        this.sessionManager = sessionManager;
        this.likeListener = listener;
        this.apiService = ApiClient.getApiService();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }

    public void updateReviews(List<Review> newReviews) {
        this.reviews = newReviews;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private RatingBar ratingBar;
        private TextView tvUserName;
        private TextView tvComment;
        private TextView tvLikesCount;
        private ImageButton btnLike;
        private TextView tvDate;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            btnLike = itemView.findViewById(R.id.btnLike);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(Review review) {
            ratingBar.setRating(review.getRating() != null ? review.getRating() : 0);
            ratingBar.setEnabled(false);
            
            tvUserName.setText(review.getUserName() != null ? review.getUserName() : "Unknown");
            tvComment.setText(review.getComment() != null ? review.getComment() : "");
            tvDate.setText(formatDate(review.getCreatedAt()));
            
            // Update likes count
            int likes = review.getLikes() != null ? review.getLikes() : 0;
            tvLikesCount.setText(String.valueOf(likes));
            
            // Set like button state
            boolean isLiked = review.getIsLiked() != null && review.getIsLiked();
            btnLike.setSelected(isLiked);
            btnLike.setImageResource(isLiked ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
            
            // Handle like button click
            btnLike.setOnClickListener(v -> handleLikeClick(review));
        }

        private void handleLikeClick(Review review) {
            String token = sessionManager.getAuthToken();
            if (token == null || token.isEmpty()) {
                Toast.makeText(itemView.getContext(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isCurrentlyLiked = review.getIsLiked() != null && review.getIsLiked();

            if (isCurrentlyLiked) {
                // Unlike
                apiService.unlikeReview("Bearer " + token, review.getId())
                        .enqueue(new Callback<com.example.ute.models.response.ApiResponse>() {
                            @Override
                            public void onResponse(Call<com.example.ute.models.response.ApiResponse> call,
                                    Response<com.example.ute.models.response.ApiResponse> response) {
                                if (response.isSuccessful()) {
                                    review.setIsLiked(false);
                                    review.setLikes((review.getLikes() != null ? review.getLikes() : 1) - 1);
                                    notifyItemChanged(getAdapterPosition());
                                    if (likeListener != null) {
                                        likeListener.onLikeChanged(review);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<com.example.ute.models.response.ApiResponse> call, Throwable t) {
                                Toast.makeText(itemView.getContext(), "Lỗi khi xóa like", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Like
                apiService.likeReview("Bearer " + token, review.getId())
                        .enqueue(new Callback<com.example.ute.models.response.ApiResponse>() {
                            @Override
                            public void onResponse(Call<com.example.ute.models.response.ApiResponse> call,
                                    Response<com.example.ute.models.response.ApiResponse> response) {
                                if (response.isSuccessful()) {
                                    review.setIsLiked(true);
                                    review.setLikes((review.getLikes() != null ? review.getLikes() : 0) + 1);
                                    notifyItemChanged(getAdapterPosition());
                                    if (likeListener != null) {
                                        likeListener.onLikeChanged(review);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<com.example.ute.models.response.ApiResponse> call, Throwable t) {
                                Toast.makeText(itemView.getContext(), "Lỗi khi thêm like", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

        private String formatDate(String createdAt) {
            if (createdAt == null) return "";
            // Format: "2026-01-07 20:33:27" -> "7/1/2026"
            try {
                String[] parts = createdAt.split(" ");
                String datePart = parts[0]; // "2026-01-07"
                String[] dateParts = datePart.split("-");
                return dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0];
            } catch (Exception e) {
                return createdAt;
            }
        }
    }
}
