package com.example.todo_list;

import android.app.Dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class task_adapter extends RecyclerView.Adapter<task_adapter.ViewHolder> {
    private ArrayList<Task_Model> model =new ArrayList<>();
    private Context context;
    private Database_helper helper;

    public task_adapter(Context context,Database_helper helper) {
        this.context = context;
        this.helper = helper;
    }

    @NonNull
    @Override
    public task_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        ViewHolder holder = new ViewHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull task_adapter.ViewHolder holder, int position) {
        Task_Model model = this.model.get(position);
        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.check.setChecked(model.getStatus());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public void setModel(ArrayList<Task_Model> model) {
        this.model = model;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        TextView title,desc,date,time;
        ImageButton imgbtn;
        MenuItem edit,del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.status);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.txt_date);
            time = itemView.findViewById(R.id.txt_time);
            imgbtn = itemView.findViewById(R.id.settings);
            edit = itemView.findViewById(R.id.menu_edit);
            del = itemView.findViewById(R.id.menu_del);

            //updating the status of task when checkbox is clicked
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Task_Model model = task_adapter.this.model.get(getAdapterPosition());
                    boolean status = check.isChecked();
                    model.setStatus(status);
                    helper.getWritableDatabase();
                    helper.updateStatus(model.getId(),status);
                }
            });

            PopupMenu popupMenu = new PopupMenu(context,imgbtn);
            popupMenu.getMenuInflater().inflate(R.menu.img_menu,popupMenu.getMenu());
            imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu.show();
                }
            });
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Task_Model model = task_adapter.this.model.get(getAdapterPosition());
                    if (item.getItemId() == R.id.menu_edit) {
                        edit_task_dialog(model);
                        return true;
                    } else if (item.getItemId() == R.id.menu_del) {
                        helper.getWritableDatabase();
                        helper.delete(model);
                        task_adapter.this.model.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        Toast.makeText(context, "Task Deleted Successfully", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            });
        }

        private void edit_task_dialog(Task_Model model) {
            Dialog edit_dialog = new Dialog(context);
            edit_dialog.setContentView(R.layout.edit_dialog_box);
            edit_dialog.show();
            edit_dialog.setCancelable(false);
            edit_task(edit_dialog,model);
        }

        private void edit_task(Dialog editDialog, Task_Model model) {
            ImageButton cancelbtn = editDialog.findViewById(R.id.cancelbtn);
            Button savebtn = editDialog.findViewById(R.id.savebtn);
            EditText edit_title = editDialog.findViewById(R.id.edit_title);
            EditText edit_desc = editDialog.findViewById(R.id.edit_desc);

            //getting the data from selected task
            edit_title.setText(model.getTitle());
            edit_desc.setText(model.getDescription());

            //cancel button
            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editDialog.dismiss();
                }
            });

            //save button
            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edit_title.getText().toString().isEmpty()){
                        Toast.makeText(context, "Invalid data", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        model.setTitle(edit_title.getText().toString());
                        model.setDescription(edit_desc.getText().toString());
                        helper.getWritableDatabase();
                        helper.update_task(model);
                        task_adapter.this.model.set(getAdapterPosition(),model);
                        notifyItemChanged(getAdapterPosition());
                        editDialog.dismiss();
                    }
                }
            });
        }
    }
}
