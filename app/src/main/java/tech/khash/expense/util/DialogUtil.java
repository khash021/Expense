package tech.khash.expense.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tech.khash.expense.R;
import tech.khash.expense.adapter.ExpenseTypeAdapter;
import tech.khash.expense.model.ExpenseEntity;

public class DialogUtil {


    public static class ShowDeleteConfirmationDialog extends AlertDialog {
        private Context context;
        private DeleteDialogListener listener;

        public ShowDeleteConfirmationDialog(@NonNull Context context,
                                            @NonNull final DeleteDialogListener deleteDialogListener) {
            super(context);
            this.context = context;
            this.listener = deleteDialogListener;
            createShowDialog();
        }

        private void createShowDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.warning))
                    .setMessage(context.getString(R.string.delete_all_message))
                    .setPositiveButton(context.getString(R.string.delete), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onDeleteSelected();
                        }
                    })
                    .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onCancelSelected();
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }

        public interface DeleteDialogListener {
            void onDeleteSelected();

            void onCancelSelected();
        }

    }

    public static class ExpenseTypeDialog extends DialogFragment implements ExpenseTypeAdapter.ListClickListener {
        private Context context;
        private ExpenseTypeDialogListener listener;

        public ExpenseTypeDialog(@NonNull Context context, ExpenseTypeDialogListener listener) {
            this.context = context;
            this.listener = listener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            ConstraintLayout view = (ConstraintLayout) View
                    .inflate(context, R.layout.alert_dialog_expense_list, null);

            ImageButton cancelButton = view.findViewById(R.id.alert_dialog_expense_type_cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            RecyclerView recyclerView = view.findViewById(R.id.alert_dialog_expense_type_recycler_view);

            ExpenseTypeAdapter adapter = new ExpenseTypeAdapter(context, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,
                    false));
            DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(decoration);

            return new android.app.AlertDialog.Builder(getActivity()).setView(
                    view).create();
        }

        @Override
        public void onListItemClick(@ExpenseEntity.ExpenseTypeInt int position) {
            if (listener != null)
                //array index starts at zero, our types starts at 1
                listener.onExpenseSelected(position + 1);
            dismiss();
        }

        public interface ExpenseTypeDialogListener {
            void onDismiss();

            void onExpenseSelected(int expenseType);
        }
    }


//    public static void showDeleteConfirmationDialog(@NonNull Context context,
//                                                    @NonNull final DialodUtil.DeleteDialogListener deleteDialogListener) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                .setTitle(context.getString(R.string.warning))
//                .setMessage(context.getString(R.string.delete_all_message))
//                .setPositiveButton(context.getString(R.string.delete), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onListItemClick(DialogInterface dialog, int which) {
//                        deleteDialogListener.onDeleteSelected();
//                    }
//                })
//                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onListItemClick(DialogInterface dialog, int which) {
//                        deleteDialogListener.onCancelSelected();
//                        dialog.dismiss();
//                    }
//                });
//        builder.create().show();
//    }
//
//    public interface DeleteDialogListener {
//        void onDeleteSelected();
//        void onCancelSelected();
//    }
}