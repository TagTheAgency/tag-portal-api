package com.tagtheagency.portal.briefs.model;

import java.util.Date;
import java.util.Map;

/**
 * 
<table>
  <thead>
    <tr>
      <th>Attribute</th>
      <th>Type</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code class="highlighter-rouge">id</code></td>
      <td>integer</td>
      <td>Unique ID for the time entry.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">spent_date</code></td>
      <td>date</td>
      <td>Date of the time entry.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">user</code></td>
      <td>object</td>
      <td>An object containing the id and name of the associated user.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">user_assignment</code></td>
      <td>object</td>
      <td>A <a href="/api-v2/projects-api/projects/user-assignments/#the-user-assignment-object">user assignment object</a> of the associated user.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">client</code></td>
      <td>object</td>
      <td>An object containing the id and name of the associated client.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">project</code></td>
      <td>object</td>
      <td>An object containing the id and name of the associated project.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">task</code></td>
      <td>object</td>
      <td>An object containing the id and name of the associated task.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">task_assignment</code></td>
      <td>object</td>
      <td>A <a href="/api-v2/projects-api/projects/task-assignments/#the-task-assignment-object">task assignment object</a> of the associated task.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">external_reference</code></td>
      <td>object</td>
      <td>An object containing the id, group_id, permalink, service, and service_icon_url of the associated external reference.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">invoice</code></td>
      <td>object</td>
      <td>Once the time entry has been invoiced, this field will include the associated invoice’s id and number.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">hours</code></td>
      <td>decimal</td>
      <td>Number of (decimal time) hours tracked in this time entry.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">notes</code></td>
      <td>string</td>
      <td>Notes attached to the time entry.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">is_locked</code></td>
      <td>boolean</td>
      <td>Whether or not the time entry has been locked.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">locked_reason</code></td>
      <td>string</td>
      <td>Why the time entry has been locked.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">is_closed</code></td>
      <td>boolean</td>
      <td>Whether or not the time entry has been approved via Timesheet Approval.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">is_billed</code></td>
      <td>boolean</td>
      <td>Whether or not the time entry has been marked as invoiced.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">timer_started_at</code></td>
      <td>datetime</td>
      <td>Date and time the timer was started (if tracking by duration).</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">started_time</code></td>
      <td>time</td>
      <td>Time the time entry was started (if tracking by start/end times).</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">ended_time</code></td>
      <td>time</td>
      <td>Time the time entry was ended (if tracking by start/end times).</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">is_running</code></td>
      <td>boolean</td>
      <td>Whether or not the time entry is currently running.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">billable</code></td>
      <td>boolean</td>
      <td>Whether or not the time entry is billable.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">budgeted</code></td>
      <td>boolean</td>
      <td>Whether or not the time entry counts towards the project budget.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">billable_rate</code></td>
      <td>decimal</td>
      <td>The billable rate for the time entry.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">cost_rate</code></td>
      <td>decimal</td>
      <td>The cost rate for the time entry.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">created_at</code></td>
      <td>datetime</td>
      <td>Date and time the time entry was created.</td>
    </tr>
    <tr>
      <td><code class="highlighter-rouge">updated_at</code></td>
      <td>datetime</td>
      <td>Date and time the time entry was last updated.</td>
    </tr>
  </tbody>
</table>

 * @author Colin
 *
 */
public class TimeEntry {

	private int id;
	
	private Date spentDate;
	
	private User user;
	
	private UserAssignment userAssignment;
	
	private Project project;
	
	private Task task;
	
	private TaskAssignment taskAssignment;
	
	private ExternalReference externalReference;
	
	private Map<String, Object> invoice;
	
	private double hours;
	
	private String notes;
	
	private boolean locked;
	
	private String lockedReason;
	
	private boolean closed;
	
	private boolean billed;
	
	private Date timerStartedAt;
	
	private String startedTime;
	
	private String endedTime;
	
	private boolean running;
	
	private boolean billable;
	
	private boolean budgeted;
	
	private double billableRate;
	
	private double costRate;
	
	private Date createdAt;
	
	private Date updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSpentDate() {
		return spentDate;
	}

	public void setSpentDate(Date spentDate) {
		this.spentDate = spentDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserAssignment getUserAssignment() {
		return userAssignment;
	}

	public void setUserAssignment(UserAssignment userAssignment) {
		this.userAssignment = userAssignment;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskAssignment getTaskAssignment() {
		return taskAssignment;
	}

	public void setTaskAssignment(TaskAssignment taskAssignment) {
		this.taskAssignment = taskAssignment;
	}

	public ExternalReference getExternalReference() {
		return externalReference;
	}

	public void setExternalReference(ExternalReference externalReference) {
		this.externalReference = externalReference;
	}

	public Map<String, Object> getInvoice() {
		return invoice;
	}

	public void setInvoice(Map<String, Object> invoice) {
		this.invoice = invoice;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getLockedReason() {
		return lockedReason;
	}

	public void setLockedReason(String lockedReason) {
		this.lockedReason = lockedReason;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean isBilled() {
		return billed;
	}

	public void setBilled(boolean billed) {
		this.billed = billed;
	}

	public Date getTimerStartedAt() {
		return timerStartedAt;
	}

	public void setTimerStartedAt(Date timerStartedAt) {
		this.timerStartedAt = timerStartedAt;
	}

	public String getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(String startedTime) {
		this.startedTime = startedTime;
	}

	public String getEndedTime() {
		return endedTime;
	}

	public void setEndedTime(String endedTime) {
		this.endedTime = endedTime;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isBillable() {
		return billable;
	}

	public void setBillable(boolean billable) {
		this.billable = billable;
	}

	public boolean isBudgeted() {
		return budgeted;
	}

	public void setBudgeted(boolean budgeted) {
		this.budgeted = budgeted;
	}

	public double getBillableRate() {
		return billableRate;
	}

	public void setBillableRate(double billableRate) {
		this.billableRate = billableRate;
	}

	public double getCostRate() {
		return costRate;
	}

	public void setCostRate(double costRate) {
		this.costRate = costRate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
